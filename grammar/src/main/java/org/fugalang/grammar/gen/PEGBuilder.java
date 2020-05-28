package org.fugalang.grammar.gen;

import org.fugalang.core.parser.RuleType;
import org.fugalang.grammar.classbuilder.*;
import org.fugalang.grammar.peg.*;
import org.fugalang.grammar.util.ParserStringUtil;

import java.util.LinkedHashMap;
import java.util.Map;

public class PEGBuilder {

    private final Map<String, OrRule> ruleMap;
    private final TokenConverter converter;
    private final Map<String, String> classNameMap = new LinkedHashMap<>();
    private final ClassSet classSet;

    private static final boolean REQUIRED = false;
    private static final boolean OPTIONAL = true;

    public PEGBuilder(
            Rules rules,
            TokenConverter converter,
            PackageOutput packageOutput
    ) {
        ruleMap = new LinkedHashMap<>();
        for (var rule : rules.singleRules()) {
            ruleMap.put(rule.name(), rule.orRule());
        }
        this.converter = converter;
        classSet = new ClassSet(packageOutput);
    }

    public void generate(boolean toFiles) {
        if (!classSet.getClasses().isEmpty()) {
            throw new IllegalStateException("Cannot repeat generation");
        }

        generateClasses();

        if (toFiles) {
            classSet.writeToFiles();
        }
    }

    public void generateClasses() {
        // do this first because each rule needs to lookup the types of previous rules
        for (var entry : ruleMap.entrySet()) {
            classNameMap.put(entry.getKey(), ParserStringUtil.convertCase(entry.getKey()));
        }

        for (var entry : ruleMap.entrySet()) {
            var left_recursive = isLeftRecursive(entry.getKey(), entry.getValue());

            var realClassName = classNameMap.get(entry.getKey());
            var className = ClassName.of(realClassName, entry.getKey());

            // use a root class to reduce files
            ClassBuilder cb = classSet.createRootClass(className, left_recursive);

            var rule_repr = entry.getKey() + ": " + PEGUtil.constructString(entry.getValue());
            cb.setHeaderComments(rule_repr);
            cb.setRuleType(RuleType.Disjunction);

            addOrRule(className, cb, entry.getValue());

            // protect against not initializing result
            cb.guardMatchEmptyString();

            // for checking invariant state
            classSet.markRootClassDone();
        }
    }

    private void addOrRule(ClassName className, ClassBuilder cb, OrRule rule) {
        if (rule.orRule2s().isEmpty()) {
            // only one rule - can propagate fields of this class
            // but need to change the type here
            cb.setRuleType(RuleType.Conjunction);
            addAndRule(className, cb, rule.andRule(), REQUIRED);
        } else {

            // For counting component classes
            int class_count = 1;

            // must create new classes for AND rules that have more than one
            // REPEAT rule
            for (AndRule andRule : PEGUtil.allAndRules(rule)) {

                // add class count to the name, even if not used, because
                // otherwise could result in the same class names later,
                // when there are two branches
                var newClassName = className.suffix(class_count);
                class_count++;

                if (andRule.repeats().size() == 1) {
                    // only one repeat rule - can propagate fields of this class
                    addAndRule(newClassName, cb, andRule, REQUIRED);
                } else {
                    // need to make a new class for this, because
                    // a list can't hold multiple-ly typed objects
                    var component_cb = classSet.createComponentClass(newClassName);

                    var rule_repr = PEGUtil.constructString(andRule);
                    component_cb.setHeaderComments(rule_repr);
                    component_cb.setRuleType(RuleType.Conjunction);

                    var smart_name = getSmartName(newClassName, andRule);

                    // Add a field to the class set
                    // The reason to do this first is that if adding the rule fails,
                    // this class can still show that this point was reached
                    addRepeatField(newClassName, cb, smart_name,
                            RepeatType.Once, REQUIRED, ResultSource.ofClass(newClassName));

                    addAndRule(newClassName, component_cb, andRule, REQUIRED);
                }
            }
        }
    }

    private void addAndRule(
            ClassName className,
            ClassBuilder cb,
            AndRule rule,
            boolean isOptional
    ) {
        if (rule.repeats().size() == 1) {
            addRepeatedSubRule(className, cb, rule.repeats().get(0), isOptional);
        } else {
            // don't need to check for component classes - every RepeatRule
            // can be on a single field

            // still need to change the names though, since there may be two
            // parallel groups

            // it's also why there's an if condition: if only one rule, there
            // shouldn't be numbering

            int class_count = 1;

            for (var repeatRule : rule.repeats()) {
                var classWithCount = className.suffix(class_count);
                class_count++;

                addRepeatedSubRule(classWithCount, cb, repeatRule, isOptional);
            }
        }
    }

    private void addRepeatedSubRule(
            ClassName className,
            ClassBuilder cb,
            Repeat repeatRule,
            boolean isOptional
    ) {
        Item item = repeatRule.item();

        var repeatType = PEGUtil.getRepeatType(repeatRule);

        switch (PEGUtil.getRuleType(item)) {
            case Group:
                addOrRuleAsComponent(className, cb, item.group().orRule(),
                        repeatType, REQUIRED);
                break;
            case Optional:
                addOrRuleAsComponent(className, cb,
                        item.optional().orRule(), repeatType, OPTIONAL);
                break;
            case Token:
                addToken(cb, repeatType, PEGUtil.getItemString(item), isOptional);
                break;
        }
    }

    private void addToken(
            ClassBuilder cb,
            RepeatType repeatType,
            String token,
            boolean isOptional
    ) {

        if (classNameMap.containsKey(token)) {
            // it is referring to another class.

            var classType = classNameMap.get(token);
            var className = ClassName.of(classType, token);

            // fix - need to add repeat rules here
            addRepeatField(className, cb, className.decapName(),
                    repeatType, isOptional, ResultSource.ofClass(className));
        } else {

            // not another class generated by the parser
            // add a token value instead

            var convertedValue = converter.checkToken(token).orElseThrow();

            var classType = convertedValue.getClassName();

            // this is used to unambiguously refer to two list references of the same token
            var ruleName = cb.getRuleName() + ":" + convertedValue.getFieldName().toLowerCase();
            var className = ClassName.of(classType, ruleName);

            if (classType.equals("boolean")) {
                var fieldName = ParserStringUtil
                        .prefixCap("is", convertedValue.getFieldName());
                var resultSource = ResultSource.ofTokenLiteral(convertedValue.getSourceLiteral());

                addRepeatField(className, cb, fieldName, repeatType, isOptional, resultSource);
            } else {
                var fieldName = convertedValue.getFieldName();
                cb.addImport(converter.getTokenTypeClass());
                var resultSource = ResultSource.ofTokenType(converter.getTokenTypeShort() +
                        "." + convertedValue.getSourceLiteral());

                addRepeatField(className, cb, fieldName, repeatType, isOptional, resultSource);
            }
        }
    }

    private void addRepeatField(
            ClassName className,
            ClassBuilder cb,
            String fieldName,
            RepeatType repeatType,
            boolean isOptional,
            ResultSource resultSource
    ) {
        if (repeatType == RepeatType.Once) {
            var fieldType = isOptional ? FieldType.Optional : FieldType.Required;
            var field = new ClassField(className, fieldName, fieldType, resultSource);
            cb.addField(field);
        } else {
            cb.addImport("java.util.List");
            var newClassName = className.asList();

            // pluralize the field name
            var newFieldName = fieldName + "s";

            var fieldType = repeatType == RepeatType.OnceOrMore ?
                    FieldType.RequiredList : FieldType.OptionalList;
            var field = new ClassField(newClassName, newFieldName, fieldType, resultSource);
            cb.addField(field);
        }
    }


    private void addOrRuleAsComponent(
            ClassName className,
            ClassBuilder cb,
            OrRule rule,
            RepeatType repeatType,
            boolean isOptional
    ) {
        // maybe this can just be added to this class
        // but maybe there needs to be a separate class

        if (rule.orRule2s().isEmpty() && rule.andRule().repeats().size() == 1 &&
                repeatType == RepeatType.Once) {
            // ^fix - single-char repeats

            // just add all the repeat rules and be done with it
            addAndRule(className, cb, rule.andRule(), isOptional);
        } else {

            var component_cb = classSet.createComponentClass(className);
            var rule_repr = PEGUtil.constructString(rule);
            component_cb.setHeaderComments(rule_repr);
            component_cb.setRuleType(RuleType.Disjunction);

            var smart_name = getSmartName(className, rule);

            // Add a field to the class set
            // The reason to do this first is that if adding the rule fails,
            // this class can still show that this point was reached
            addRepeatField(className, cb, smart_name,
                    repeatType, isOptional, ResultSource.ofClass(className));

            addOrRule(className, component_cb, rule);
        }
    }

    public String getSmartName(ClassName className, AndRule andRule) {
        if (andRule.repeats().size() <= 3 &&
                andRule.repeats().stream().allMatch(PEGUtil::isSingle)) {

            StringBuilder sb = null;
            for (var rule : andRule.repeats()) {
                var itemString = PEGUtil.getItemString(rule.item());
                if (sb == null) sb = new StringBuilder();
                if (ParserStringUtil.isWord(itemString)) {
                    sb.append(ParserStringUtil.convertCase(itemString));
                } else {
                    sb.append(converter.checkToken(itemString).orElseThrow().getFieldName());
                }
            }
            if (sb != null) {
                return ParserStringUtil.decap(sb.toString());
            }
            throw new IllegalArgumentException();
        }
        return className.decapName();
    }


    public String getSmartName(ClassName className, OrRule orRule) {
        var andList = orRule.orRule2s();
        if (andList.isEmpty()) {
            return getSmartName(className, orRule.andRule());
        }
        if (andList.size() == 1) {
            return getSmartName(className, orRule.andRule()) +
                    "Or" + ParserStringUtil.capitalizeFirstChar(getSmartName(className, andList.get(0).andRule()));
        }
        return className.decapName();
    }

    private static String getFirstName(OrRule rule) {
        var repeatRules = rule.andRule().repeats();
        if (repeatRules.isEmpty()) return null;
        var sub = repeatRules.get(0).item();
        return sub.hasName() ? sub.name() : null;
    }

    public static boolean isLeftRecursive(String name, OrRule rule) {
        return !rule.orRule2s().isEmpty() && name.equals(getFirstName(rule));
    }
}

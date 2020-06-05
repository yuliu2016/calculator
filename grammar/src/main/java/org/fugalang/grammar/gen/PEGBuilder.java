package org.fugalang.grammar.gen;

import org.fugalang.core.parser.RuleType;
import org.fugalang.grammar.classbuilder.*;
import org.fugalang.grammar.peg.AndRule;
import org.fugalang.grammar.peg.OrRule;
import org.fugalang.grammar.peg.Repeat;
import org.fugalang.grammar.peg.Rules;
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

    private void addOrRule(
            ClassName className,
            ClassBuilder cb,
            OrRule rule
    ) {
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
                    addField(newClassName,
                            cb,
                            smart_name,
                            RepeatType.Once,
                            REQUIRED,
                            ResultSource.ofClass(newClassName),
                            null);

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
            addRepeatedItem(className, cb, rule.repeats().get(0), isOptional);
        } else {
            // don't need to check for component classes - every RepeatRule
            // can be on a single field

            // still need to change the names though, since there may be two
            // parallel groups

            // it's also why there's an if condition: if only one rule, there
            // shouldn't be numbering

            int class_count = 1;

            for (var repeat : rule.repeats()) {
                var classWithCount = className.suffix(class_count);
                class_count++;

                addRepeatedItem(classWithCount, cb, repeat, isOptional);
            }
        }
    }

    private void addRepeatedItem(
            ClassName className,
            ClassBuilder cb,
            Repeat repeat,
            boolean isOptional
    ) {
        var item = PEGUtil.getRepeatItem(repeat);
        var repeatType = PEGUtil.getRepeatType(repeat);

        var delimiter = repeat.hasDelimited() ? repeat.delimited().string() : null;

        switch (PEGUtil.getRuleType(item)) {
            case Group:
                addOrRuleAsComponent(className, cb, item.group().orRule(),
                        repeatType, REQUIRED, delimiter);
                break;
            case Optional:
                addOrRuleAsComponent(className, cb,
                        item.optional().orRule(), repeatType, OPTIONAL, delimiter);
                break;
            case Token:
                addToken(cb, repeatType, PEGUtil.getItemString(item), isOptional, delimiter);
                break;
        }
    }

    private void addOrRuleAsComponent(
            ClassName className,
            ClassBuilder cb,
            OrRule rule,
            RepeatType repeatType,
            boolean isOptional,
            String delimiter
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
            addField(className,
                    cb,
                    smart_name,
                    repeatType,
                    isOptional,
                    ResultSource.ofClass(className),
                    delimiter);

            addOrRule(className, component_cb, rule);
        }
    }

    private void addToken(
            ClassBuilder cb,
            RepeatType repeatType,
            String token,
            boolean isOptional,
            String delimiter
    ) {

        if (classNameMap.containsKey(token)) {
            // it is referring to another class.

            var classType = classNameMap.get(token);
            var className = ClassName.of(classType, token);

            // fix - need to add repeat rules here
            addField(className,
                    cb,
                    className.decapName(),
                    repeatType,
                    isOptional,
                    ResultSource.ofClass(className),
                    delimiter);
        } else {

            // not another class generated by the parser
            // add a token value instead

            var convertedValue = converter.checkToken(token);

            var classType = convertedValue.getClassName();

            // this is used to unambiguously refer to two list references of the same token
            var ruleName = cb.getRuleName() + ":" + convertedValue.getFieldName().toLowerCase();
            var className = ClassName.of(classType, ruleName);

            if (classType.equals("boolean")) {
                var fieldName = ParserStringUtil
                        .prefixCap("is", convertedValue.getFieldName());
                var resultSource = ResultSource.ofTokenLiteral(convertedValue.getSourceLiteral());

                addField(className,
                        cb,
                        fieldName,
                        repeatType,
                        isOptional,
                        resultSource,
                        delimiter);
            } else {
                var fieldName = convertedValue.getFieldName();
                cb.addImport(converter.getTokenTypeClass());
                var resultSource = ResultSource.ofTokenType(converter.getTokenTypeShort() +
                        "." + convertedValue.getSourceLiteral());

                addField(className,
                        cb,
                        fieldName,
                        repeatType,
                        isOptional,
                        resultSource,
                        delimiter);
            }
        }
    }

    private void addField(
            ClassName className,
            ClassBuilder cb,
            String fieldName,
            RepeatType repeatType,
            boolean isOptional,
            ResultSource resultSource,
            String delimiter
    ) {
        ClassName newClassName;
        String newFieldName;
        FieldType fieldType;
        if (repeatType == RepeatType.Once) {
            newClassName = className;
            newFieldName = fieldName;
            fieldType = isOptional ? FieldType.Optional : FieldType.Required;
        } else {
            cb.addImport("java.util.List");
            newClassName = className.asList();

            // pluralize the field name
            newFieldName = fieldName + "s";
            fieldType = repeatType == RepeatType.OnceOrMore ?
                    FieldType.RequiredList : FieldType.OptionalList;

        }
        var field = new ClassField(newClassName, newFieldName, fieldType, resultSource, delimiter);
        cb.addField(field);
    }

    public String getSmartName(ClassName className, AndRule andRule) {
        if (andRule.repeats().size() <= 3 &&
                andRule.repeats().stream().allMatch(PEGUtil::isSingle)) {

            StringBuilder sb = null;
            for (var rule : andRule.repeats()) {
                var itemString = PEGUtil.getItemString(PEGUtil.getRepeatItem(rule));
                if (sb == null) sb = new StringBuilder();
                if (ParserStringUtil.isWord(itemString)) {
                    sb.append(ParserStringUtil.convertCase(itemString));
                } else {
                    sb.append(converter.checkToken(itemString).getFieldName());
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
        var repeats = rule.andRule().repeats();
        if (repeats.isEmpty()) return null;
        var sub = PEGUtil.getRepeatItem(repeats.get(0));
        return sub.hasName() ? sub.name() : null;
    }

    public static boolean isLeftRecursive(String name, OrRule rule) {
        return !rule.orRule2s().isEmpty() && name.equals(getFirstName(rule));
    }
}

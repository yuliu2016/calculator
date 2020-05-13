package org.fugalang.core.grammar.gen;

import org.fugalang.core.grammar.classbuilder.*;
import org.fugalang.core.grammar.pgen.AndRule;
import org.fugalang.core.grammar.pgen.OrRule;
import org.fugalang.core.grammar.pgen.RepeatRule;
import org.fugalang.core.grammar.pgen.Rules;
import org.fugalang.core.grammar.psi.RepeatType;
import org.fugalang.core.grammar.util.ParserStringUtil;
import org.fugalang.core.parser.RuleType;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

public class PEGBuilder {

    private final Map<String, OrRule> ruleMap;
    private final TokenConverter converter;
    private final Map<String, String> classNameMap = new LinkedHashMap<>();
    private final ClassSet classSet;
    private final String tokenTypeClass;
    private final String tokenTypeClassShort;

    private static final boolean REQUIRED = false;
    private static final boolean OPTIONAL = true;

    public PEGBuilder(
            Rules rules,
            TokenConverter converter,
            Path path,
            String packageName,
            String tokenTypeClass
    ) {
        ruleMap = new LinkedHashMap<>();
        for (var rule : rules.singleRuleList()) {
            ruleMap.put(rule.token(), rule.orRule());
        }
        this.converter = converter;

        classSet = new ClassSet(path, packageName);

        this.tokenTypeClass = tokenTypeClass;

        var split = tokenTypeClass.split("\\.");
        tokenTypeClassShort = split[split.length - 1];
    }

    public void generate(boolean toFiles) {
        if (!classSet.getClasses().isEmpty()) {
            throw new IllegalStateException("Cannot repeat generation");
        }

        generateClasses();

        if (toFiles) {
            classSet.writeToFiles();
        } else {
            for (var classWithComponents : classSet.getClasses()) {
                System.out.println(classWithComponents.generateClassCode());
            }
        }
    }

    public void generateClasses() {
        // do this first because each rule needs to lookup the types of previous rules
        for (var entry : ruleMap.entrySet()) {
            classNameMap.put(entry.getKey(), ParserStringUtil.convertCase(entry.getKey()));
        }

        for (var entry : ruleMap.entrySet()) {
            var realClassName = classNameMap.get(entry.getKey());
            var className = ClassName.of(realClassName, entry.getKey());

            // use a root class to reduce files
            ClassBuilder cb = classSet.createRootClass(className);

            cb.setHeaderComments(entry.getKey() + ": " + PEGCompat.constructString(entry.getValue()));
            cb.setRuleType(RuleType.Disjunction);

            addOrRule(className, cb, entry.getValue());

            // protect against not initializing result
            cb.guardMatchEmptyString();

            // for checking invariant state
            classSet.markRootClassDone();
        }
    }

    private void addOrRule(ClassName className, ClassBuilder cb, OrRule rule) {
        if (rule.orRule2List().isEmpty()) {
            // only one rule - can propagate fields of this class
            // but need to change the type here
            cb.setRuleType(RuleType.Conjunction);
            addAndRule(className, cb, rule.andRule(), REQUIRED);
        } else {

            // For counting component classes
            int class_count = 1;

            // must create new classes for AND rules that have more than one
            // REPEAT rule
            for (AndRule andRule : PEGCompat.allAndRules(rule)) {

                // add class count to the name, even if not used, because
                // otherwise could result in the same class names later,
                // when there are two branches
                var newClassName = className.suffix(class_count);
                class_count++;

                if (andRule.andRule2List().isEmpty()) {
                    // only one repeat rule - can propagate fields of this class
                    addAndRule(newClassName, cb, andRule, REQUIRED);
                } else {
                    // need to make a new class for this, because
                    // a list can't hold multiple-ly typed objects
                    var component_cb = classSet.createComponentClass(newClassName);

                    component_cb.setHeaderComments(PEGCompat.constructString(andRule));
                    component_cb.setRuleType(RuleType.Conjunction);

                    // Add a field to the class set
                    // The reason to do this first is that if adding the rule fails,
                    // this class can still show that this point was reached
                    addRepeatField(newClassName, cb, newClassName.decapName(),
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

        if (rule.andRule2List().isEmpty()) {
            addRepeatedSubRule(className, cb, rule.repeatRule(), isOptional);
        } else {
            // don't need to check for component classes - every RepeatRule
            // can be on a single field

            // still need to change the names though, since there may be two
            // parallel groups

            // it's also why there's an if condition: if only one rule, there
            // shouldn't be numbering

            int class_count = 1;

            for (RepeatRule repeatRule : PEGCompat.allRepeatRules(rule)) {
                var classWithCount = className.suffix(class_count);
                class_count++;

                addRepeatedSubRule(classWithCount, cb, repeatRule, isOptional);
            }
        }
    }

    private void addRepeatedSubRule(
            ClassName className,
            ClassBuilder cb,
            RepeatRule repeatRule,
            boolean isOptional
    ) {
        var subRule = repeatRule.subRule();

        var repeatType = PEGCompat.getRepeatType(repeatRule);

        switch (PEGCompat.getRuleType(subRule)) {
            case Group -> addOrRuleAsComponent(className, cb, subRule.subRule1().orRule(),
                    repeatType, REQUIRED);

            case Optional -> addOrRuleAsComponent(className, cb,
                    subRule.subRule2().orRule(), repeatType, OPTIONAL);

            case Token -> addToken(cb, repeatType, subRule.token(), isOptional);
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

            var convertedValue = converter.checkToken(token).orElseThrow(() ->
                    new IllegalStateException("SubRule token " + token + " is invalid!"));

            var classType = convertedValue.getClassName();
            var className = ClassName.of(classType);

            if (classType.equals("boolean")) {
                var fieldName = ParserStringUtil
                        .prefixCap("isToken", convertedValue.getFieldName());
                var resultSource = ResultSource.ofTokenLiteral(convertedValue.getSourceLiteral());

                addRepeatField(className, cb, fieldName, repeatType, isOptional, resultSource);
            } else {
                var fieldName = convertedValue.getFieldName();
                cb.addImport(tokenTypeClass);
                var resultSource = ResultSource.ofTokenType(tokenTypeClassShort +
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
            var newClassName = className.wrapIn("List");
            var newFieldName = fieldName + "List";

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

        if (rule.orRule2List().isEmpty() && rule.andRule().andRule2List().isEmpty() &&
                repeatType == RepeatType.Once) {
            // ^fix - single-char repeats

            // just add all the repeat rules and be done with it
            addAndRule(className, cb, rule.andRule(), isOptional);
        } else {

            var component_cb = classSet.createComponentClass(className);
            component_cb.setHeaderComments(PEGCompat.constructString(rule));
            component_cb.setRuleType(RuleType.Disjunction);

            // Add a field to the class set
            // The reason to do this first is that if adding the rule fails,
            // this class can still show that this point was reached
            addRepeatField(className, cb, className.decapName(),
                    repeatType, isOptional, ResultSource.ofClass(className));

            addOrRule(className, component_cb, rule);
        }
    }
}

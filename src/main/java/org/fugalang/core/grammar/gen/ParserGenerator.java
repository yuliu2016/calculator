package org.fugalang.core.grammar.gen;

import org.fugalang.core.grammar.classbuilder.*;
import org.fugalang.core.grammar.psi.*;
import org.fugalang.core.parser.RuleType;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * An automatic parser generator
 *
 * <h2>General Rules</h2>
 * <li>A field may contain a single token, or another rule</li>
 * <li>If the token is a literal, it uses the boolean type</li>
 * <li>If the token has a value (e.g. Number, String), it is an Object type</li>
 * <li>A field may also be a repeat rule</li>
 * <li>If repeat rule is *, only use one field called rule*List</li>
 * <li>If repeat rule is +, use two fields</li>
 * <li>Field 1: named rule*</li>
 * <li>Field 2: named ruleList</li>
 *
 * <h2>Atomic Rules</h2>
 * <li>A rule is <i>atomic</i> when it can be represented in one field</li>
 * <li>A repeat rule is atomic when its subrule is atomic</li>
 *
 * <h2>Class Rules</h2>
 * <li>A class can represent an And rule or an Or rule</li>
 */
public class ParserGenerator {
    private final Map<String, OrRule> ruleMap;
    private final TokenConverter converter;
    private final Map<String, String> classNameMap = new LinkedHashMap<>();
    private final ClassSet classSet;
    private final String tokenTypeClass;
    private final String tokenTypeClassShort;


    private static final boolean REQUIRED = false;
    private static final boolean OPTIONAL = true;

    public ParserGenerator(
            Rules rules,
            TokenConverter converter,
            Path path,
            String packageName,
            String tokenTypeClass
    ) {
        ruleMap = new LinkedHashMap<>();
        for (var rule : rules.rules) {
            ruleMap.put(rule.name, rule.orRule);
        }
        this.converter = converter;

        classSet = new ClassSet(path, packageName);

        this.tokenTypeClass = tokenTypeClass;

        var split = tokenTypeClass.split("\\.");
        tokenTypeClassShort = split[split.length - 1];
    }

    @SuppressWarnings("unused")
    public void validateRules() {
        for (OrRule rule : ruleMap.values()) {
            validateOrRule(rule);
        }
    }

    private void validateOrRule(OrRule rule) {
        validateAndRule(rule.andRule);
        for (AndRule andRule : rule.andRules) {
            validateAndRule(andRule);
        }
    }

    private void validateAndRule(AndRule andRule) {
        validateRepeatRule(andRule.repeatRule);
        for (RepeatRule repeatRule : andRule.repeatRules) {
            validateRepeatRule(repeatRule);
        }
    }

    private void validateRepeatRule(RepeatRule rule) {
        validateSubRule(rule.subRule);
    }

    private void validateSubRule(SubRule rule) {
        if (rule.groupedOrRule != null) {
            validateOrRule(rule.groupedOrRule);
        } else if (rule.optionalOrRule != null) {
            validateOrRule(rule.optionalOrRule);
        } else {
            if (!ruleMap.containsKey(rule.token) && !converter.didResolveToken(rule.token)) {
                throw new IllegalStateException("'" + rule.token + "' doesn't exist!!!");
            }
        }
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

            cb.setHeaderComments(entry.getKey() + ": " + entry.getValue().toSimpleString());
            cb.setRuleType(RuleType.Disjunction);

            addOrRule(className, cb, entry.getValue());

            // protect against not initializing result
            cb.guardMatchEmptyString();

            // for checking invariant state
            classSet.markRootClassDone();
        }
    }

    private void addOrRule(ClassName className, ClassBuilder cb, OrRule rule) {
        if (rule.andRules.isEmpty()) {
            // only one rule - can propagate fields of this class
            // but need to change the type here
            cb.setRuleType(RuleType.Conjunction);
            addAndRule(className, cb, rule.andRule, REQUIRED);
        } else {

            // For counting component classes
            int class_count = 1;

            // must create new classes for AND rules that have more than one
            // REPEAT rule
            for (AndRule andRule : rule.allAndRules()) {

                // add class count to the name, even if not used, because
                // otherwise could result in the same class names later,
                // when there are two branches
                var newClassName = className.suffix(class_count);
                class_count++;

                if (andRule.repeatRules.isEmpty()) {
                    // only one repeat rule - can propagate fields of this class
                    addAndRule(newClassName, cb, andRule, REQUIRED);
                } else {
                    // need to make a new class for this, because
                    // a list can't hold multiple-ly typed objects
                    var component_cb = classSet.createComponentClass(newClassName);

                    component_cb.setHeaderComments(andRule.toSimpleString());
                    component_cb.setRuleType(RuleType.Conjunction);

                    // Add a field to the class set
                    // The reason to do this first is that if adding the rule fails,
                    // this class can still show that this point was reached
                    addRepeatField(newClassName, cb, newClassName.decapName(),
                            RepeatType.Once, REQUIRED, ResultSource.ofClass(newClassName));

                    addAndRule(newClassName, component_cb, andRule, REQUIRED);

                    // protect against not initializing result
                    component_cb.guardMatchEmptyString();
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

        if (rule.repeatRules.isEmpty()) {
            addRepeatedSubRule(className, cb, rule.repeatRule, isOptional);
        } else {
            // don't need to check for component classes - every RepeatRule
            // can be on a single field

            // still need to change the names though, since there may be two
            // parallel groups

            // it's also why there's an if condition: if only one rule, there
            // shouldn't be numbering

            int class_count = 1;

            for (RepeatRule repeatRule : rule.allRepeatRules()) {
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
        var subRule = repeatRule.subRule;

        switch (subRule.type) {
            case Group -> addOrRuleAsComponent(className, cb, subRule.groupedOrRule,
                    repeatRule.type, REQUIRED);

            case Optional -> addOrRuleAsComponent(className, cb,
                    subRule.optionalOrRule, repeatRule.type, OPTIONAL);

            case Token -> addToken(cb, repeatRule.type, subRule, isOptional);
        }
    }

    private void addToken(
            ClassBuilder cb,
            RepeatType repeatType,
            SubRule subRule,
            boolean isOptional
    ) {
        if (classNameMap.containsKey(subRule.token)) {
            // it is referring to another class.

            var classType = classNameMap.get(subRule.token);
            var className = ClassName.of(classType, subRule.token);

            // fix - need to add repeat rules here
            addRepeatField(className, cb, className.decapName(),
                    repeatType, isOptional, ResultSource.ofClass(className));
        } else {

            // not another class generated by the parser
            // add a token value instead

            var convertedValue = converter.checkToken(subRule.token).orElseThrow(() ->
                    new IllegalStateException("SubRule token " + subRule.token + " is invalid!"));

            var classType = convertedValue.getClassName();
            var className = ClassName.of(classType);

            if (classType.equals("boolean")) {
                var fieldName = ParserStringUtil
                        .prefixCap("isToken", convertedValue.getFieldName());
                var resultSource = ResultSource.ofTokenLiteral(convertedValue.getSourceLiteral());

                addRepeatField(className, cb, fieldName, repeatType, REQUIRED, resultSource);
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
            cb.addImport("java.util.ArrayList");
            cb.addImport("java.util.Collections");
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

        if (rule.andRules.isEmpty() && rule.andRule.repeatRules.isEmpty() &&
                repeatType == RepeatType.Once) {
            // ^fix - single-char repeats

            // just add all the repeat rules and be done with it
            addAndRule(className, cb, rule.andRule, isOptional);
        } else {

            var component_cb = classSet.createComponentClass(className);
            component_cb.setHeaderComments(rule.toSimpleString());
            component_cb.setRuleType(RuleType.Disjunction);

            // Add a field to the class set
            // The reason to do this first is that if adding the rule fails,
            // this class can still show that this point was reached
            addRepeatField(className, cb, className.decapName(),
                    repeatType, isOptional, ResultSource.ofClass(className));

            addOrRule(className, component_cb, rule);

            // protect against not initializing result
            component_cb.guardMatchEmptyString();
        }
    }
}

package org.fugalang.core.grammar.gen;

import org.fugalang.core.grammar.classbuilder.ClassBuilder;
import org.fugalang.core.grammar.classbuilder.ClassName;
import org.fugalang.core.grammar.classbuilder.ClassSet;
import org.fugalang.core.grammar.psi.*;

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

    public ParserGenerator(
            Rules rules,
            TokenConverter converter,
            Path path,
            String packageName
    ) {
        ruleMap = new LinkedHashMap<>();
        for (var rule : rules.rules) {
            ruleMap.put(rule.name, rule.orRule);
        }
        this.converter = converter;
        validate();

        classSet = new ClassSet(path, packageName);
    }

    public void validate() {
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
        assert !(rule.tokenPlus && rule.tokenStar);
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
            classNameMap.put(entry.getKey(), ParseStringUtil.convertCase(entry.getKey()));
        }

        for (var entry : ruleMap.entrySet()) {
            var realClassName = classNameMap.get(entry.getKey());

            // use a root class to reduce files
            ClassBuilder cb = classSet.createRootClass(realClassName);
            cb.setHeaderComments(entry.getKey() + ": " + entry.getValue().toSimpleString());

            addOrRule(ClassName.of(realClassName), cb, entry.getValue());

            // for checking invariant state
            classSet.markRootClassDone();
        }
    }

    private void addOrRule(ClassName className, ClassBuilder cb, OrRule rule) {
        if (rule.andRules.isEmpty()) {
            // only one rule - can propagate fields of this class
            addAndRule(className, cb, rule.andRule, false);
        } else {

            // For counting component classes
            int class_count = 1;

            // must create new classes for AND rules that have more than one
            // REPEAT rule
            for (AndRule andRule : rule.allAndRules()) {

                // add class count to the name, even if not used, because
                // otherwise could result in the same class names later,
                // when there are two branches
                var classWithCount = className.suffix(class_count);
                class_count++;

                if (andRule.repeatRules.isEmpty()) {
                    // only one repeat rule - can propagate fields of this class
                    addAndRule(classWithCount, cb, andRule, false);
                } else {
                    var classType = classWithCount.asType();

                    // need to make a new class for this, because
                    // a list can't hold multiple-ly typed objects
                    var component_cb = classSet.createComponentClass(classType);
                    component_cb.setHeaderComments(andRule.toSimpleString());

                    // Add a field to the class set
                    // The reason to do this first is that if adding the rule fails,
                    // this class can still show that this point was reached
                    cb.addFieldByClassName(classWithCount,
                            ParseStringUtil.decap(classType), false);

                    addAndRule(classWithCount, component_cb, andRule, false);
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

        var sub = repeatRule.subRule;

        if (sub.groupedOrRule != null) {
            addOrRuleAsComponent(className, cb, sub.groupedOrRule, repeatRule, false);
        } else if (sub.optionalOrRule != null) {
            addOrRuleAsComponent(className, cb, sub.optionalOrRule, repeatRule, true);
        } else if (sub.token != null) {

            if (classNameMap.containsKey(sub.token)) {
                // it is referring to another class.

                var classType = classNameMap.get(sub.token);
                cb.addFieldByClassName(ClassName.of(classType),
                        ParseStringUtil.decap(classType), isOptional);
            } else {

                // not another class generated by the parser
                // add a token value instead

                var convertedValue = converter.checkToken(sub.token).orElseThrow(() ->
                        new IllegalStateException("SubRule token " + sub.token + " is invalid!"));

                var clsName = convertedValue.getClassName();

                if (clsName.equals("boolean")) {
                    var fieldName = ParseStringUtil.prefixCap("isToken", convertedValue.getFieldName());
                    addFieldWithRepeat(ClassName.of("boolean"), cb, fieldName, repeatRule, false);
                } else {
                    addFieldWithRepeat(ClassName.of(clsName), cb,
                            convertedValue.getFieldName(), repeatRule, isOptional);
                }
            }
        }
    }

    private void addFieldWithRepeat(
            ClassName className,
            ClassBuilder cb,
            String fieldName,
            RepeatRule repeatRule,
            boolean isOptional
    ) {

        if (repeatRule.tokenStar) {
            cb.addImport("java.util.List");
            cb.addFieldByClassName(className.wrapIn("List"), fieldName + "List", isOptional);
        } else if (repeatRule.tokenPlus) {
            cb.addFieldByClassName(className, fieldName, isOptional);
            cb.addImport("java.util.List");
            cb.addFieldByClassName(className.wrapIn("List"), fieldName + "List", isOptional);
        } else {
            cb.addFieldByClassName(className, fieldName, isOptional);
        }
    }


    private void addOrRuleAsComponent(
            ClassName className,
            ClassBuilder cb,
            OrRule rule,
            RepeatRule repeatRule,
            boolean isOptional
    ) {
        // maybe this can just be added to this class
        // but maybe there needs to be a separate class

        if (rule.andRules.isEmpty() && rule.andRule.repeatRules.isEmpty() &&
                !(repeatRule.tokenPlus || repeatRule.tokenStar)) {
            // ^fix - single-char repeats

            // just add all the repeat rules and be done with it
            addAndRule(className, cb, rule.andRule, isOptional);
        } else {
            var classWithSuffix = className.suffix("Group");
            var classType = classWithSuffix.asType();

            var component_cb = classSet.createComponentClass(classType);
            component_cb.setHeaderComments(rule.toSimpleString());

            // Add a field to the class set
            // The reason to do this first is that if adding the rule fails,
            // this class can still show that this point was reached
            addFieldWithRepeat(classWithSuffix, cb,
                    ParseStringUtil.decap(classType), repeatRule, isOptional);

            addOrRule(classWithSuffix, component_cb, rule);
        }
    }
}

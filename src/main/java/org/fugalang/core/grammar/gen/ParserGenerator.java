package org.fugalang.core.grammar.gen;

import org.fugalang.core.grammar.classbuilder.ClassBuilder;
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
        classSet.getBuilders().clear();
        generateClasses();
        if (toFiles) {
            classSet.writeToFiles();
        } else {
            for (ClassBuilder builder : classSet.getBuilders()) {
                System.out.println(builder.generateClassCode());
            }
        }
    }

    public void generateClasses() {
        // do this first because each rule needs to lookup the types of previous rules
        for (var entry : ruleMap.entrySet()) {
            classNameMap.put(entry.getKey(), ParseStringUtil.convertCase(entry.getKey()));
        }

        for (var entry : ruleMap.entrySet()) {
            var className = classNameMap.get(entry.getKey());
            ClassBuilder cb = classSet.create(className);
            cb.setHeaderComments(entry.getKey() + ": " + entry.getValue().toSimpleString());

            addOrRule(className, cb, entry.getValue());
        }
    }

    private void addOrRule(String className, ClassBuilder cb, OrRule rule) {
        if (rule.andRules.isEmpty()) {
            // only one rule - can propagate fields of this class
            addAndRule(className, cb, rule.andRule);
        } else {

            // For counting component classes
            int class_count = 1;

            // must create new classes for AND rules that have more than one
            // REPEAT rule
            for (AndRule andRule : rule.allAndRules()) {

                // add class count to the name, even if not used, because
                // otherwise could result in the same class names later,
                // when there are two branches
                String class_with_count = className + class_count;
                class_count++;

                if (andRule.repeatRules.isEmpty()) {
                    // only one repeat rule - can propagate fields of this class
                    addAndRule(class_with_count, cb, andRule);
                } else {
                    // need to make a new class for this, because
                    // a list can't hold multiple-ly typed objects
                    var component_cb = classSet.create(class_with_count);
                    component_cb.setHeaderComments(andRule.toSimpleString());

                    // Add a field to the class set
                    // The reason to do this first is that if adding the rule fails,
                    // this class can still show that this point was reached
                    cb.addField(class_with_count, ParseStringUtil.decap(class_with_count));

                    addAndRule(class_with_count, component_cb, andRule);
                }
            }
        }
    }

    private void addAndRule(String className, ClassBuilder cb, AndRule rule) {

        if (rule.repeatRules.isEmpty()) {
            addRepeatedSubRule(className, cb, rule.repeatRule);
        } else {
            // don't need to check for component classes - every RepeatRule
            // can be on a single field

            // still need to change the names though, since there may be two
            // parallel groups

            // it's also why there's an if condition: if only one rule, there
            // shouldn't be numbering

            int class_count = 1;

            for (RepeatRule repeatRule : rule.allRepeatRules()) {
                String class_with_count = className + class_count;
                class_count++;

                addRepeatedSubRule(class_with_count, cb, repeatRule);
            }
        }
    }

    private void addRepeatedSubRule(String className, ClassBuilder cb, RepeatRule repeatRule) {

        var sub = repeatRule.subRule;

        if (sub.groupedOrRule != null) {
            addOrRuleAsComponent(className, cb, sub.groupedOrRule, repeatRule);
        } else if (sub.optionalOrRule != null) {
            addOrRuleAsComponent(className, cb, sub.optionalOrRule, repeatRule);
        } else if (sub.token != null) {

            if (classNameMap.containsKey(sub.token)) {

                // it is referring to another class.

                var classRef = classNameMap.get(sub.token);
                cb.addField(classRef, ParseStringUtil.decap(classRef));
            } else {

                // not another class generated by the parser
                // add a token value instead

                var convertedValue = converter.checkToken(sub.token).orElseThrow(() ->
                        new IllegalStateException("SubRule token " + sub.token + " is invalid!"));

                var clsName = convertedValue.getClassName();

                if (clsName.equals("boolean")) {
                    var fieldName = ParseStringUtil.prefixCap("isToken", convertedValue.getFieldName());
                    addFieldWithRepeat("boolean", cb, fieldName, repeatRule);
                } else {
                    addFieldWithRepeat(clsName, cb, convertedValue.getFieldName(), repeatRule);
                }
            }
        }
    }

    private void addFieldWithRepeat(
            String className, ClassBuilder cb, String fieldName, RepeatRule repeatRule) {

        // fix for boolean
        var objClassName = ParseStringUtil.capitalizeFirstCharOnly(className);

        if (repeatRule.tokenStar) {
            cb.addField("List<" + objClassName + ">", fieldName + "List");
        } else if (repeatRule.tokenPlus) {
            cb.addField(className, fieldName);
            cb.addField("List<" + objClassName + ">", fieldName + "List");
        } else {
            cb.addField(className, fieldName);
        }
    }


    private void addOrRuleAsComponent(String className, ClassBuilder cb, OrRule rule, RepeatRule repeatRule) {
        // maybe this can just be added to this class
        // but maybe there needs to be a separate class

        if (rule.andRules.isEmpty() && rule.andRule.repeatRules.isEmpty() &&
                !(repeatRule.tokenPlus || repeatRule.tokenStar)) {
            // ^fix - single-char repeats

            // just add all the repeat rules and be done with it
            addAndRule(className, cb, rule.andRule);
        } else {
            String class_with_postfix = className + "Group";

            var component_cb = classSet.create(class_with_postfix);
            component_cb.setHeaderComments(rule.toSimpleString());

            // Add a field to the class set
            // The reason to do this first is that if adding the rule fails,
            // this class can still show that this point was reached
            addFieldWithRepeat(class_with_postfix, cb,
                    ParseStringUtil.decap(class_with_postfix), repeatRule);

            addOrRule(class_with_postfix, component_cb, rule);
        }
    }
}

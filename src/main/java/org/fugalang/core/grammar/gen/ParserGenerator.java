package org.fugalang.core.grammar.gen;

import org.fugalang.core.grammar.classbuilder.ClassBuilder;
import org.fugalang.core.grammar.classbuilder.ClassSet;
import org.fugalang.core.grammar.psi.*;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

public class ParserGenerator {
    private final Map<String, OrRule> ruleMap;
    private final TokenConverter validator;
    private final Map<String, String> classNames = new LinkedHashMap<>();

    public ParserGenerator(Rules rules, TokenConverter validator) {
        ruleMap = new LinkedHashMap<>();
        for (var rule : rules.rules) {
            ruleMap.put(rule.name, rule.orRule);
        }
        this.validator = validator;
        validate();
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
            if (!ruleMap.containsKey(rule.token) && !validator.didResolveToken(rule.token)) {
                throw new IllegalStateException("'" + rule.token + "' doesn't exist!!!");
            }
        }
    }

    public void generate(Path path, String pkg) {
        ClassSet classSet = new ClassSet(path, pkg);
        generateClasses(classSet);
        for (ClassBuilder builder : classSet.getBuilders()) {
            System.out.println(builder.getClassCode());
        }
    }

    public void generateClasses(ClassSet classSet) {
        // do this first because each rule needs to lookup the types of previous rules
        for (var entry: ruleMap.entrySet()) {
            classNames.put(entry.getKey(), CaseUtil.convertCase(entry.getKey()));
        }

        for (var entry : ruleMap.entrySet()) {
            var className = classNames.get(entry.getKey());
            appendOrRuleFields(classSet, className, entry.getValue());
        }
    }

    private void appendOrRuleFields(ClassSet classSet, String className, OrRule orRule) {
        classSet.create(className);
    }
}

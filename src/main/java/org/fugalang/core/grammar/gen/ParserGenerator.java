package org.fugalang.core.grammar.gen;

import org.fugalang.core.grammar.psi.*;

import java.util.LinkedHashMap;
import java.util.Map;

public class ParserGenerator {
    private final Map<String, OrRule> ruleMap;
    private final TokenValidator validator;

    public ParserGenerator(Rules rules, TokenValidator validator) {
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
            if (!ruleMap.containsKey(rule.token) && !validator.isValidToken(rule.token)) {
                throw new IllegalStateException("'" + rule.token + "' doesn't exist!!!");
            }
        }
    }
}

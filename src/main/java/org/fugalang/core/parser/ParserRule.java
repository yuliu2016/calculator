package org.fugalang.core.parser;

public class ParserRule {
    private final String ruleName;
    private final RuleType ruleType;

    private ParserRule(String ruleName, RuleType ruleType) {
        this.ruleName = ruleName;
        this.ruleType = ruleType;
    }

    public String getRuleName() {
        return ruleName;
    }

    public RuleType getRuleType() {
        return ruleType;
    }

    @Override
    public String toString() {
        return ruleName;
    }

    public static ParserRule of(String ruleName, RuleType ruleType) {
        return new ParserRule(ruleName, ruleType);
    }

    public static ParserRule and_rule(String ruleName) {
        return of(ruleName, RuleType.Conjunction);
    }

    public static ParserRule or_rule(String ruleName) {
        return of(ruleName, RuleType.Disjunction);
    }
}

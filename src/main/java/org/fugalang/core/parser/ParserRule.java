package org.fugalang.core.parser;

public class ParserRule {
    private final String ruleName;
    private final RuleType ruleType;
    private final boolean isExplicit;

    public ParserRule(String ruleName, RuleType ruleType) {
        this(ruleName, ruleType, true);
    }

    public ParserRule(String ruleName, RuleType ruleType, boolean isExplicit) {
        this.ruleName = ruleName;
        this.ruleType = ruleType;
        this.isExplicit = isExplicit;
    }

    public String getRuleName() {
        return ruleName;
    }

    public RuleType getRuleType() {
        return ruleType;
    }

    public boolean isExplicit() {
        return isExplicit;
    }

    @Override
    public String toString() {
        return ruleName;
    }
}

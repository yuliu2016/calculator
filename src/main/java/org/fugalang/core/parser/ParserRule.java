package org.fugalang.core.parser;

public class ParserRule {
    private final String ruleName;
    private final RuleType ruleType;
    private final boolean isLeftRecursive;

    private ParserRule(String ruleName, RuleType ruleType, boolean isLeftRecursive) {
        this.ruleName = ruleName;
        this.ruleType = ruleType;
        this.isLeftRecursive = isLeftRecursive;
    }

    public String getRuleName() {
        return ruleName;
    }

    public RuleType getRuleType() {
        return ruleType;
    }

    public boolean isLeftRecursive() {
        return isLeftRecursive;
    }

    @Override
    public String toString() {
        return ruleName;
    }

    public static ParserRule and_rule(String ruleName) {
        return new ParserRule(ruleName, RuleType.Conjunction, false);
    }

    public static ParserRule or_rule(String ruleName) {
        return new ParserRule(ruleName, RuleType.Disjunction, false);
    }

    public static ParserRule leftrec_rule(String ruleName) {
        return new ParserRule(ruleName, RuleType.Disjunction, true);
    }

}

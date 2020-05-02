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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParserRule that = (ParserRule) o;

        if (!ruleName.equals(that.ruleName)) return false;
        return ruleType == that.ruleType;
    }

    @Override
    public int hashCode() {
        int result = ruleName.hashCode();
        result = 31 * result + ruleType.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ParserRule{" +
                "ruleName='" + ruleName + '\'' +
                '}';
    }
}

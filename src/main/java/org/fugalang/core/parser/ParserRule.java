package org.fugalang.core.parser;

public record ParserRule(
        String ruleName,
        RuleType ruleType,
        boolean isLeftRecursive
) {

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

package org.fugalang.core.grammar.pgen;

import org.fugalang.core.parser.*;

/**
 * single_rule: 'TOK' ':' 'or_rule' 'NEWLINE'
 */
public final class SingleRule extends ConjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("single_rule", RuleType.Conjunction, true);

    private final String token;
    private final boolean isTokenColon;
    private final OrRule orRule;
    private final boolean isTokenNewline;

    public SingleRule(
            String token,
            boolean isTokenColon,
            OrRule orRule,
            boolean isTokenNewline
    ) {
        this.token = token;
        this.isTokenColon = isTokenColon;
        this.orRule = orRule;
        this.isTokenNewline = isTokenNewline;
    }

    @Override
    protected void buildRule() {
        addRequired("token", token());
        addRequired("isTokenColon", isTokenColon());
        addRequired("orRule", orRule());
        addRequired("isTokenNewline", isTokenNewline());
    }

    public String token() {
        return token;
    }

    public boolean isTokenColon() {
        return isTokenColon;
    }

    public OrRule orRule() {
        return orRule;
    }

    public boolean isTokenNewline() {
        return isTokenNewline;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeTokenType("TOK");
        result = result && parseTree.consumeTokenLiteral(":");
        result = result && OrRule.parse(parseTree, level + 1);
        result = result && parseTree.consumeTokenLiteral("NEWLINE");

        parseTree.exit(level, marker, result);
        return result;
    }
}

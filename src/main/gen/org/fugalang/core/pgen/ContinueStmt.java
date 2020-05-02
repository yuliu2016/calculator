package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * continue_stmt: 'continue'
 */
public final class ContinueStmt extends ConjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("continue_stmt", RuleType.Conjunction, true);

    private final boolean isTokenContinue;

    public ContinueStmt(
            boolean isTokenContinue
    ) {
        this.isTokenContinue = isTokenContinue;
    }

    @Override
    protected void buildRule() {
        addRequired("isTokenContinue", isTokenContinue());
    }

    public boolean isTokenContinue() {
        return isTokenContinue;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeTokenLiteral("continue");

        parseTree.exit(level, marker, result);
        return result;
    }
}

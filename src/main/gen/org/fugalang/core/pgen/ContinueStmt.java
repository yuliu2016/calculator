package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import org.fugalang.core.parser.ParseTree;

/**
 * continue_stmt: 'continue'
 */
public final class ContinueStmt extends ConjunctionRule {
    public static final String RULE_NAME = "continue_stmt";

    private final boolean isTokenContinue;

    public ContinueStmt(
            boolean isTokenContinue
    ) {
        this.isTokenContinue = isTokenContinue;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addRequired("isTokenContinue", isTokenContinue);
    }

    public boolean isTokenContinue() {
        return isTokenContinue;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        boolean result;

        result = parseTree.consumeTokenLiteral("continue");

        parseTree.exit(level, marker, result);
        return result;
    }
}

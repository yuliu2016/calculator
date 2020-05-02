package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * pass_stmt: 'pass'
 */
public final class PassStmt extends ConjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("pass_stmt", RuleType.Conjunction, true);

    private final boolean isTokenPass;

    public PassStmt(
            boolean isTokenPass
    ) {
        this.isTokenPass = isTokenPass;
    }

    @Override
    protected void buildRule() {
        addRequired("isTokenPass", isTokenPass());
    }

    public boolean isTokenPass() {
        return isTokenPass;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeTokenLiteral("pass");

        parseTree.exit(level, marker, result);
        return result;
    }
}

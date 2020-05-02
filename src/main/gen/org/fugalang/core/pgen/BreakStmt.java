package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * break_stmt: 'break'
 */
public final class BreakStmt extends ConjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("break_stmt", RuleType.Conjunction, true);

    private final boolean isTokenBreak;

    public BreakStmt(
            boolean isTokenBreak
    ) {
        this.isTokenBreak = isTokenBreak;
    }

    @Override
    protected void buildRule() {
        addRequired("isTokenBreak", isTokenBreak());
    }

    public boolean isTokenBreak() {
        return isTokenBreak;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeTokenLiteral("break");

        parseTree.exit(level, marker, result);
        return result;
    }
}

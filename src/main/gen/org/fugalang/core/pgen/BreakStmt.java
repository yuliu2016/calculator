package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * break_stmt: 'break'
 */
public final class BreakStmt extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("break_stmt", RuleType.Conjunction);

    public static BreakStmt of(ParseTreeNode node) {
        return new BreakStmt(node);
    }

    private BreakStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consumeToken("break");
        t.exit(r);
        return r;
    }
}

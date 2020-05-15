package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * break_stmt: 'break'
 */
public final class BreakStmt extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("break_stmt", RuleType.Conjunction, true);

    public static BreakStmt of(ParseTreeNode node) {
        return new BreakStmt(node);
    }

    private BreakStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = t.consumeToken("break");
        t.exit(r);
        return r;
    }
}

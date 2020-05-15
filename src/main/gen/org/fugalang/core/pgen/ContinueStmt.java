package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * continue_stmt: 'continue'
 */
public final class ContinueStmt extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("continue_stmt", RuleType.Conjunction);

    public static ContinueStmt of(ParseTreeNode node) {
        return new ContinueStmt(node);
    }

    private ContinueStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consume("continue");
        t.exit(r);
        return r;
    }
}

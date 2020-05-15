package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * pass_stmt: 'pass'
 */
public final class PassStmt extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("pass_stmt", RuleType.Conjunction);

    public static PassStmt of(ParseTreeNode node) {
        return new PassStmt(node);
    }

    private PassStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consumeToken("pass");
        t.exit(r);
        return r;
    }
}

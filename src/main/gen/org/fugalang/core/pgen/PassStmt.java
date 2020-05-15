package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * pass_stmt: 'pass'
 */
public final class PassStmt extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("pass_stmt", RuleType.Conjunction, true);

    public static PassStmt of(ParseTreeNode node) {
        return new PassStmt(node);
    }

    private PassStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = t.consumeToken("pass");
        t.exit(r);
        return r;
    }
}

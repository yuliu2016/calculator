package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * continue_stmt: 'continue'
 */
public final class ContinueStmt extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("continue_stmt", RuleType.Conjunction, true);

    public static ContinueStmt of(ParseTreeNode node) {
        return new ContinueStmt(node);
    }

    private ContinueStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = t.consumeToken("continue");
        t.exit(r);
        return r;
    }
}

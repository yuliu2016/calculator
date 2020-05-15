package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * return_stmt: 'return' ['exprlist_star']
 */
public final class ReturnStmt extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("return_stmt", RuleType.Conjunction);

    public static ReturnStmt of(ParseTreeNode node) {
        return new ReturnStmt(node);
    }

    private ReturnStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    public ExprlistStar exprlistStar() {
        return ExprlistStar.of(getItem(1));
    }

    public boolean hasExprlistStar() {
        return hasItemOfRule(1, ExprlistStar.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consumeToken("return");
        if (r) ExprlistStar.parse(t, lv + 1);
        t.exit(r);
        return r;
    }
}

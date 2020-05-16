package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * slice_expr: ':' ['expr']
 */
public final class SliceExpr extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("slice_expr", RuleType.Conjunction);

    public static SliceExpr of(ParseTreeNode node) {
        return new SliceExpr(node);
    }

    private SliceExpr(ParseTreeNode node) {
        super(RULE, node);
    }

    public Expr expr() {
        return get(1, Expr::of);
    }

    public boolean hasExpr() {
        return has(1, Expr.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consume(":");
        if (r) Expr.parse(t, lv + 1);
        t.exit(r);
        return r;
    }
}

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
}

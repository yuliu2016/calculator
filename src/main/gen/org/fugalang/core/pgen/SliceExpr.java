package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.FugaRules;

/**
 * slice_expr: ':' ['expr']
 */
public final class SliceExpr extends NodeWrapper {

    public SliceExpr(ParseTreeNode node) {
        super(FugaRules.SLICE_EXPR, node);
    }

    public Expr expr() {
        return get(1, Expr.class);
    }

    public boolean hasExpr() {
        return has(1);
    }
}

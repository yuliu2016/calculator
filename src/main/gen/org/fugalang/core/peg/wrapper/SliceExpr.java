package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * slice_expr: ':' [expr]
 */
public final class SliceExpr extends NodeWrapper {

    public SliceExpr(ParseTreeNode node) {
        super(node);
    }

    public Expr expr() {
        return new Expr(get(1));
    }

    public boolean hasExpr() {
        return has(1);
    }
}

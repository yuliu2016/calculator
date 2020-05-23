package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * slice: [expr] slice_expr [slice_expr] | expr
 */
public final class Slice extends NodeWrapper {

    public Slice(ParseTreeNode node) {
        super(node);
    }

    public Slice1 slice1() {
        return get(0, Slice1.class);
    }

    public boolean hasSlice1() {
        return has(0);
    }

    public Expr expr() {
        return get(1, Expr.class);
    }

    public boolean hasExpr() {
        return has(1);
    }

    /**
     * [expr] slice_expr [slice_expr]
     */
    public static final class Slice1 extends NodeWrapper {

        public Slice1(ParseTreeNode node) {
            super(node);
        }

        public Expr expr() {
            return get(0, Expr.class);
        }

        public boolean hasExpr() {
            return has(0);
        }

        public SliceExpr sliceExpr() {
            return get(1, SliceExpr.class);
        }

        public SliceExpr sliceExpr1() {
            return get(2, SliceExpr.class);
        }

        public boolean hasSliceExpr1() {
            return has(2);
        }
    }
}

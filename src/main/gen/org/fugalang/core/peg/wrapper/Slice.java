package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * slice:
 * *   | [expr] slice_expr [slice_expr]
 * *   | expr
 */
public final class Slice extends NodeWrapper {

    public Slice(ParseTreeNode node) {
        super(node);
    }

    public Slice1 slice1() {
        return new Slice1(get(0));
    }

    public boolean hasSlice1() {
        return has(0);
    }

    public Expr expr() {
        return new Expr(get(1));
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
            return new Expr(get(0));
        }

        public boolean hasExpr() {
            return has(0);
        }

        public SliceExpr sliceExpr() {
            return new SliceExpr(get(1));
        }

        public SliceExpr sliceExpr1() {
            return new SliceExpr(get(2));
        }

        public boolean hasSliceExpr1() {
            return has(2);
        }
    }
}

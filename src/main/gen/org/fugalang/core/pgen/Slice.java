package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

/**
 * slice: ['expr'] 'slice_expr' ['slice_expr'] | 'expr'
 */
public final class Slice extends NodeWrapper {

    public Slice(ParseTreeNode node) {
        super(ParserRules.SLICE, node);
    }

    public Slice1 slice1() {
        return get(0, Slice1::new);
    }

    public boolean hasSlice1() {
        return has(0, ParserRules.SLICE_1);
    }

    public Expr expr() {
        return get(1, Expr::new);
    }

    public boolean hasExpr() {
        return has(1, ParserRules.EXPR);
    }

    /**
     * ['expr'] 'slice_expr' ['slice_expr']
     */
    public static final class Slice1 extends NodeWrapper {

        public Slice1(ParseTreeNode node) {
            super(ParserRules.SLICE_1, node);
        }

        public Expr expr() {
            return get(0, Expr::new);
        }

        public boolean hasExpr() {
            return has(0, ParserRules.EXPR);
        }

        public SliceExpr sliceExpr() {
            return get(1, SliceExpr::new);
        }

        public SliceExpr sliceExpr1() {
            return get(2, SliceExpr::new);
        }

        public boolean hasSliceExpr1() {
            return has(2, ParserRules.SLICE_EXPR);
        }
    }
}

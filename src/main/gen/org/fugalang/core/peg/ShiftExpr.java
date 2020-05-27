package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * shift_expr: shift_expr '<<' sum | shift_expr '>>' sum | sum
 */
public final class ShiftExpr extends NodeWrapper {

    public ShiftExpr(ParseTreeNode node) {
        super(node);
    }

    public ShiftExpr1 shiftExprLshiftSum() {
        return get(0, ShiftExpr1.class);
    }

    public boolean hasShiftExprLshiftSum() {
        return has(0);
    }

    public ShiftExpr2 shiftExprRshiftSum() {
        return get(1, ShiftExpr2.class);
    }

    public boolean hasShiftExprRshiftSum() {
        return has(1);
    }

    public Sum sum() {
        return get(2, Sum.class);
    }

    public boolean hasSum() {
        return has(2);
    }

    /**
     * shift_expr '<<' sum
     */
    public static final class ShiftExpr1 extends NodeWrapper {

        public ShiftExpr1(ParseTreeNode node) {
            super(node);
        }

        public ShiftExpr shiftExpr() {
            return get(0, ShiftExpr.class);
        }

        public Sum sum() {
            return get(2, Sum.class);
        }
    }

    /**
     * shift_expr '>>' sum
     */
    public static final class ShiftExpr2 extends NodeWrapper {

        public ShiftExpr2(ParseTreeNode node) {
            super(node);
        }

        public ShiftExpr shiftExpr() {
            return get(0, ShiftExpr.class);
        }

        public Sum sum() {
            return get(2, Sum.class);
        }
    }
}

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
        return new ShiftExpr1(get(0));
    }

    public boolean hasShiftExprLshiftSum() {
        return has(0);
    }

    public ShiftExpr2 shiftExprRshiftSum() {
        return new ShiftExpr2(get(1));
    }

    public boolean hasShiftExprRshiftSum() {
        return has(1);
    }

    public Sum sum() {
        return new Sum(get(2));
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
            return new ShiftExpr(get(0));
        }

        public Sum sum() {
            return new Sum(get(2));
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
            return new ShiftExpr(get(0));
        }

        public Sum sum() {
            return new Sum(get(2));
        }
    }
}

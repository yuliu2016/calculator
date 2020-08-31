package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * bitwise_and (left_recursive):
 * *   | bitwise_and '&' shift_expr
 * *   | shift_expr
 */
public final class BitwiseAnd extends NodeWrapper {

    public BitwiseAnd(ParseTreeNode node) {
        super(node);
    }

    public BitwiseAnd1 bitwiseAndBitAndShiftExpr() {
        return new BitwiseAnd1(get(0));
    }

    public boolean hasBitwiseAndBitAndShiftExpr() {
        return has(0);
    }

    public ShiftExpr shiftExpr() {
        return new ShiftExpr(get(1));
    }

    public boolean hasShiftExpr() {
        return has(1);
    }

    /**
     * bitwise_and '&' shift_expr
     */
    public static final class BitwiseAnd1 extends NodeWrapper {

        public BitwiseAnd1(ParseTreeNode node) {
            super(node);
        }

        public BitwiseAnd bitwiseAnd() {
            return new BitwiseAnd(get(0));
        }

        public ShiftExpr shiftExpr() {
            return new ShiftExpr(get(2));
        }
    }
}

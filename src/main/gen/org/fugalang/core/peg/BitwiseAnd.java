package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * bitwise_and: bitwise_and '&' shift_expr | shift_expr
 */
public final class BitwiseAnd extends NodeWrapper {

    public BitwiseAnd(ParseTreeNode node) {
        super(node);
    }

    public BitwiseAnd1 bitwiseAndBitAndShiftExpr() {
        return get(0, BitwiseAnd1.class);
    }

    public boolean hasBitwiseAndBitAndShiftExpr() {
        return has(0);
    }

    public ShiftExpr shiftExpr() {
        return get(1, ShiftExpr.class);
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
            return get(0, BitwiseAnd.class);
        }

        public ShiftExpr shiftExpr() {
            return get(2, ShiftExpr.class);
        }
    }
}

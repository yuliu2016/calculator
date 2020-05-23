package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

import java.util.List;

/**
 * shift_expr: 'sum' ('shift_op' 'sum')*
 */
public final class ShiftExpr extends NodeWrapper {

    public ShiftExpr(ParseTreeNode node) {
        super(ParserRules.SHIFT_EXPR, node);
    }

    public Sum sum() {
        return get(0, Sum::new);
    }

    public List<ShiftExpr2> shiftOpSums() {
        return getList(1, ShiftExpr2::new);
    }

    /**
     * 'shift_op' 'sum'
     */
    public static final class ShiftExpr2 extends NodeWrapper {

        public ShiftExpr2(ParseTreeNode node) {
            super(ParserRules.SHIFT_EXPR_2, node);
        }

        public ShiftOp shiftOp() {
            return get(0, ShiftOp::new);
        }

        public Sum sum() {
            return get(1, Sum::new);
        }
    }
}

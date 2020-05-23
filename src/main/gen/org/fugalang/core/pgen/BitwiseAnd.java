package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

import java.util.List;

/**
 * bitwise_and: 'shift_expr' ('&' 'shift_expr')*
 */
public final class BitwiseAnd extends NodeWrapper {

    public BitwiseAnd(ParseTreeNode node) {
        super(ParserRules.BITWISE_AND, node);
    }

    public ShiftExpr shiftExpr() {
        return get(0, ShiftExpr::new);
    }

    public List<BitwiseAnd2> shiftExprs() {
        return getList(1, BitwiseAnd2::new);
    }

    /**
     * '&' 'shift_expr'
     */
    public static final class BitwiseAnd2 extends NodeWrapper {

        public BitwiseAnd2(ParseTreeNode node) {
            super(ParserRules.BITWISE_AND_2, node);
        }

        public ShiftExpr shiftExpr() {
            return get(1, ShiftExpr::new);
        }
    }
}

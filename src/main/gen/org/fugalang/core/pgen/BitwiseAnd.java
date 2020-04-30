package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// bitwise_and: 'shift_expr' ('&' 'shift_expr')*
public final class BitwiseAnd extends ConjunctionRule {
    private final ShiftExpr shiftExpr;
    private final List<BitwiseAnd2> bitwiseAnd2List;

    public BitwiseAnd(
            ShiftExpr shiftExpr,
            List<BitwiseAnd2> bitwiseAnd2List
    ) {
        this.shiftExpr = shiftExpr;
        this.bitwiseAnd2List = bitwiseAnd2List;
    }

    @Override
    protected void buildRule() {
        addRequired("shiftExpr", shiftExpr);
        addRequired("bitwiseAnd2List", bitwiseAnd2List);
    }

    public ShiftExpr shiftExpr() {
        return shiftExpr;
    }

    public List<BitwiseAnd2> bitwiseAnd2List() {
        return bitwiseAnd2List;
    }

    // '&' 'shift_expr'
    public static final class BitwiseAnd2 extends ConjunctionRule {
        private final boolean isTokenBitAnd;
        private final ShiftExpr shiftExpr;

        public BitwiseAnd2(
                boolean isTokenBitAnd,
                ShiftExpr shiftExpr
        ) {
            this.isTokenBitAnd = isTokenBitAnd;
            this.shiftExpr = shiftExpr;
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenBitAnd", isTokenBitAnd);
            addRequired("shiftExpr", shiftExpr);
        }

        public boolean isTokenBitAnd() {
            return isTokenBitAnd;
        }

        public ShiftExpr shiftExpr() {
            return shiftExpr;
        }
    }
}

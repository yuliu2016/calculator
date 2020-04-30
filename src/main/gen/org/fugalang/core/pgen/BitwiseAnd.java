package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// bitwise_and: 'shift_expr' ('&' 'shift_expr')*
public final class BitwiseAnd extends ConjunctionRule {
    private final ShiftExpr shiftExpr;
    private final List<BitwiseAnd2Group> bitwiseAnd2GroupList;

    public BitwiseAnd(
            ShiftExpr shiftExpr,
            List<BitwiseAnd2Group> bitwiseAnd2GroupList
    ) {
        this.shiftExpr = shiftExpr;
        this.bitwiseAnd2GroupList = bitwiseAnd2GroupList;

        addRequired("shiftExpr", shiftExpr);
        addRequired("bitwiseAnd2GroupList", bitwiseAnd2GroupList);
    }

    public ShiftExpr shiftExpr() {
        return shiftExpr;
    }

    public List<BitwiseAnd2Group> bitwiseAnd2GroupList() {
        return bitwiseAnd2GroupList;
    }

    // '&' 'shift_expr'
    public static final class BitwiseAnd2Group extends ConjunctionRule {
        private final boolean isTokenBitAnd;
        private final ShiftExpr shiftExpr;

        public BitwiseAnd2Group(
                boolean isTokenBitAnd,
                ShiftExpr shiftExpr
        ) {
            this.isTokenBitAnd = isTokenBitAnd;
            this.shiftExpr = shiftExpr;

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

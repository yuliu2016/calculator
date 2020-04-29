package org.fugalang.core.pgen;

import java.util.List;

// bitwise_and: 'shift_expr' ('&' 'shift_expr')*
public class BitwiseAnd {
    public final ShiftExpr shiftExpr;
    public final List<BitwiseAnd2Group> bitwiseAnd2GroupList;

    public BitwiseAnd(
            ShiftExpr shiftExpr,
            List<BitwiseAnd2Group> bitwiseAnd2GroupList
    ) {
        this.shiftExpr = shiftExpr;
        this.bitwiseAnd2GroupList = bitwiseAnd2GroupList;
    }

    // '&' 'shift_expr'
    public static class BitwiseAnd2Group {
        public final boolean isTokenBitAnd;
        public final ShiftExpr shiftExpr;

        public BitwiseAnd2Group(
                boolean isTokenBitAnd,
                ShiftExpr shiftExpr
        ) {
            this.isTokenBitAnd = isTokenBitAnd;
            this.shiftExpr = shiftExpr;
        }
    }
}

package org.fugalang.core.pgen;

// '&' 'shift_expr'
public class BitwiseAnd2Group {
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

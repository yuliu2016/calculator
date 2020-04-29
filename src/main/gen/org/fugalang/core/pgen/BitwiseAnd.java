package org.fugalang.core.pgen;

// bitwise_and: 'shift_expr' ('&' 'shift_expr')*
public class BitwiseAnd {
    public final ShiftExpr shiftExpr;
    public final BitwiseAnd2Group bitwiseAnd2Group;

    public BitwiseAnd(
            ShiftExpr shiftExpr,
            BitwiseAnd2Group bitwiseAnd2Group
    ) {
        this.shiftExpr = shiftExpr;
        this.bitwiseAnd2Group = bitwiseAnd2Group;
    }
}

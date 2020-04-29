package org.fugalang.core.pgen;

// shift_expr: 'sum' (('<<' | '>>') 'sum')*
public class ShiftExpr {
    public final Sum sum;
    public final ShiftExpr2Group shiftExpr2Group;

    public ShiftExpr(
            Sum sum,
            ShiftExpr2Group shiftExpr2Group
    ) {
        this.sum = sum;
        this.shiftExpr2Group = shiftExpr2Group;
    }
}

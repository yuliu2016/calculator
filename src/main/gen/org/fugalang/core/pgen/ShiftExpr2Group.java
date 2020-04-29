package org.fugalang.core.pgen;

// ('<<' | '>>') 'sum'
public class ShiftExpr2Group {
    public final ShiftExpr2Group1Group shiftExpr2Group1Group;
    public final Sum sum;

    public ShiftExpr2Group(
            ShiftExpr2Group1Group shiftExpr2Group1Group,
            Sum sum
    ) {
        this.shiftExpr2Group1Group = shiftExpr2Group1Group;
        this.sum = sum;
    }
}

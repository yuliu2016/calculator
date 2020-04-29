package org.fugalang.core.pgen;

import java.util.List;

// shift_expr: 'sum' (('<<' | '>>') 'sum')*
public class ShiftExpr {
    public final Sum sum;
    public final List<ShiftExpr2Group> shiftExpr2GroupList;

    public ShiftExpr(
            Sum sum,
            List<ShiftExpr2Group> shiftExpr2GroupList
    ) {
        this.sum = sum;
        this.shiftExpr2GroupList = shiftExpr2GroupList;
    }
}

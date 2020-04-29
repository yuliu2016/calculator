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

    // ('<<' | '>>') 'sum'
    public static class ShiftExpr2Group {
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

    // '<<' | '>>'
    public static class ShiftExpr2Group1Group {
        public final boolean isTokenLshift;
        public final boolean isTokenRshift;

        public ShiftExpr2Group1Group(
                boolean isTokenLshift,
                boolean isTokenRshift
        ) {
            this.isTokenLshift = isTokenLshift;
            this.isTokenRshift = isTokenRshift;
        }
    }
}

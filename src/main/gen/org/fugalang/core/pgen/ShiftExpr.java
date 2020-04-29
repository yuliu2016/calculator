package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;
import org.fugalang.core.parser.DisjunctionRule;

// shift_expr: 'sum' (('<<' | '>>') 'sum')*
public final class ShiftExpr extends ConjunctionRule {
    private final Sum sum;
    private final List<ShiftExpr2Group> shiftExpr2GroupList;

    public ShiftExpr(
            Sum sum,
            List<ShiftExpr2Group> shiftExpr2GroupList
    ) {
        this.sum = sum;
        this.shiftExpr2GroupList = shiftExpr2GroupList;
    }

    public Sum getSum() {
        return sum;
    }

    public List<ShiftExpr2Group> getShiftExpr2GroupList() {
        return shiftExpr2GroupList;
    }

    // ('<<' | '>>') 'sum'
    public static final class ShiftExpr2Group extends ConjunctionRule {
        private final ShiftExpr2Group1Group shiftExpr2Group1Group;
        private final Sum sum;

        public ShiftExpr2Group(
                ShiftExpr2Group1Group shiftExpr2Group1Group,
                Sum sum
        ) {
            this.shiftExpr2Group1Group = shiftExpr2Group1Group;
            this.sum = sum;
        }

        public ShiftExpr2Group1Group getShiftExpr2Group1Group() {
            return shiftExpr2Group1Group;
        }

        public Sum getSum() {
            return sum;
        }
    }

    // '<<' | '>>'
    public static final class ShiftExpr2Group1Group extends DisjunctionRule {
        private final boolean isTokenLshift;
        private final boolean isTokenRshift;

        public ShiftExpr2Group1Group(
                boolean isTokenLshift,
                boolean isTokenRshift
        ) {
            this.isTokenLshift = isTokenLshift;
            this.isTokenRshift = isTokenRshift;
        }

        public boolean getIsTokenLshift() {
            return isTokenLshift;
        }

        public boolean getIsTokenRshift() {
            return isTokenRshift;
        }
    }
}

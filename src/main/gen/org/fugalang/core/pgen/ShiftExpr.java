package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;
import org.fugalang.core.parser.DisjunctionRule;

// shift_expr: 'sum' (('<<' | '>>') 'sum')*
public final class ShiftExpr extends ConjunctionRule {
    private final Sum sum;
    private final List<ShiftExpr2> shiftExpr2List;

    public ShiftExpr(
            Sum sum,
            List<ShiftExpr2> shiftExpr2List
    ) {
        this.sum = sum;
        this.shiftExpr2List = shiftExpr2List;
    }

    @Override
    protected void buildRule() {
        addRequired("sum", sum);
        addRequired("shiftExpr2List", shiftExpr2List);
    }

    public Sum sum() {
        return sum;
    }

    public List<ShiftExpr2> shiftExpr2List() {
        return shiftExpr2List;
    }

    // ('<<' | '>>') 'sum'
    public static final class ShiftExpr2 extends ConjunctionRule {
        private final ShiftExpr21 shiftExpr21;
        private final Sum sum;

        public ShiftExpr2(
                ShiftExpr21 shiftExpr21,
                Sum sum
        ) {
            this.shiftExpr21 = shiftExpr21;
            this.sum = sum;
        }

        @Override
        protected void buildRule() {
            addRequired("shiftExpr21", shiftExpr21);
            addRequired("sum", sum);
        }

        public ShiftExpr21 shiftExpr21() {
            return shiftExpr21;
        }

        public Sum sum() {
            return sum;
        }
    }

    // '<<' | '>>'
    public static final class ShiftExpr21 extends DisjunctionRule {
        private final boolean isTokenLshift;
        private final boolean isTokenRshift;

        public ShiftExpr21(
                boolean isTokenLshift,
                boolean isTokenRshift
        ) {
            this.isTokenLshift = isTokenLshift;
            this.isTokenRshift = isTokenRshift;
        }

        @Override
        protected void buildRule() {
            addChoice("isTokenLshift", isTokenLshift);
            addChoice("isTokenRshift", isTokenRshift);
        }

        public boolean isTokenLshift() {
            return isTokenLshift;
        }

        public boolean isTokenRshift() {
            return isTokenRshift;
        }
    }
}

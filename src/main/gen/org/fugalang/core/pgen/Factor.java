package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import org.fugalang.core.parser.DisjunctionRule;

// factor: ('+' | '-' | '~') 'factor' | 'power'
public final class Factor extends DisjunctionRule {
    private final Factor1 factor1;
    private final Power power;

    public Factor(
            Factor1 factor1,
            Power power
    ) {
        this.factor1 = factor1;
        this.power = power;
    }

    @Override
    protected void buildRule() {
        addChoice("factor1", factor1);
        addChoice("power", power);
    }

    public Factor1 factor1() {
        return factor1;
    }

    public Power power() {
        return power;
    }

    // ('+' | '-' | '~') 'factor'
    public static final class Factor1 extends ConjunctionRule {
        private final Factor11 factor11;
        private final Factor factor;

        public Factor1(
                Factor11 factor11,
                Factor factor
        ) {
            this.factor11 = factor11;
            this.factor = factor;
        }

        @Override
        protected void buildRule() {
            addRequired("factor11", factor11);
            addRequired("factor", factor);
        }

        public Factor11 factor11() {
            return factor11;
        }

        public Factor factor() {
            return factor;
        }
    }

    // '+' | '-' | '~'
    public static final class Factor11 extends DisjunctionRule {
        private final boolean isTokenPlus;
        private final boolean isTokenMinus;
        private final boolean isTokenBitNot;

        public Factor11(
                boolean isTokenPlus,
                boolean isTokenMinus,
                boolean isTokenBitNot
        ) {
            this.isTokenPlus = isTokenPlus;
            this.isTokenMinus = isTokenMinus;
            this.isTokenBitNot = isTokenBitNot;
        }

        @Override
        protected void buildRule() {
            addChoice("isTokenPlus", isTokenPlus);
            addChoice("isTokenMinus", isTokenMinus);
            addChoice("isTokenBitNot", isTokenBitNot);
        }

        public boolean isTokenPlus() {
            return isTokenPlus;
        }

        public boolean isTokenMinus() {
            return isTokenMinus;
        }

        public boolean isTokenBitNot() {
            return isTokenBitNot;
        }
    }
}

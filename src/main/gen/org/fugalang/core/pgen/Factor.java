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
        private final Factor11Group factor11Group;
        private final Factor factor;

        public Factor1(
                Factor11Group factor11Group,
                Factor factor
        ) {
            this.factor11Group = factor11Group;
            this.factor = factor;

            addRequired("factor11Group", factor11Group);
            addRequired("factor", factor);
        }

        public Factor11Group factor11Group() {
            return factor11Group;
        }

        public Factor factor() {
            return factor;
        }
    }

    // '+' | '-' | '~'
    public static final class Factor11Group extends DisjunctionRule {
        private final boolean isTokenPlus;
        private final boolean isTokenMinus;
        private final boolean isTokenBitNot;

        public Factor11Group(
                boolean isTokenPlus,
                boolean isTokenMinus,
                boolean isTokenBitNot
        ) {
            this.isTokenPlus = isTokenPlus;
            this.isTokenMinus = isTokenMinus;
            this.isTokenBitNot = isTokenBitNot;

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

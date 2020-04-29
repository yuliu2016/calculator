package org.fugalang.core.pgen;

// factor: ('+' | '-' | '~') 'factor' | 'power'
public class Factor {
    private final Factor1 factor1;
    private final Power power;

    public Factor(
            Factor1 factor1,
            Power power
    ) {
        this.factor1 = factor1;
        this.power = power;
    }

    public Factor1 getFactor1() {
        return factor1;
    }

    public Power getPower() {
        return power;
    }

    // ('+' | '-' | '~') 'factor'
    public static class Factor1 {
        private final Factor11Group factor11Group;
        private final Factor factor;

        public Factor1(
                Factor11Group factor11Group,
                Factor factor
        ) {
            this.factor11Group = factor11Group;
            this.factor = factor;
        }

        public Factor11Group getFactor11Group() {
            return factor11Group;
        }

        public Factor getFactor() {
            return factor;
        }
    }

    // '+' | '-' | '~'
    public static class Factor11Group {
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
        }

        public boolean getIsTokenPlus() {
            return isTokenPlus;
        }

        public boolean getIsTokenMinus() {
            return isTokenMinus;
        }

        public boolean getIsTokenBitNot() {
            return isTokenBitNot;
        }
    }
}

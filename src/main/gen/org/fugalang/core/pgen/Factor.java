package org.fugalang.core.pgen;

// factor: ('+' | '-' | '~') 'factor' | 'power'
public class Factor {
    public final Factor1 factor1;
    public final Power power;

    public Factor(
            Factor1 factor1,
            Power power
    ) {
        this.factor1 = factor1;
        this.power = power;
    }

    // ('+' | '-' | '~') 'factor'
    public static class Factor1 {
        public final Factor11Group factor11Group;
        public final Factor factor;

        public Factor1(
                Factor11Group factor11Group,
                Factor factor
        ) {
            this.factor11Group = factor11Group;
            this.factor = factor;
        }
    }

    // '+' | '-' | '~'
    public static class Factor11Group {
        public final boolean isTokenPlus;
        public final boolean isTokenMinus;
        public final boolean isTokenBitNot;

        public Factor11Group(
                boolean isTokenPlus,
                boolean isTokenMinus,
                boolean isTokenBitNot
        ) {
            this.isTokenPlus = isTokenPlus;
            this.isTokenMinus = isTokenMinus;
            this.isTokenBitNot = isTokenBitNot;
        }
    }
}

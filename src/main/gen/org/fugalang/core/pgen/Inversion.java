package org.fugalang.core.pgen;

// inversion: 'not' 'inversion' | 'comparison'
public class Inversion {
    public final Inversion1 inversion1;
    public final Comparison comparison;

    public Inversion(
            Inversion1 inversion1,
            Comparison comparison
    ) {
        this.inversion1 = inversion1;
        this.comparison = comparison;
    }

    // 'not' 'inversion'
    public static class Inversion1 {
        public final boolean isTokenNot;
        public final Inversion inversion;

        public Inversion1(
                boolean isTokenNot,
                Inversion inversion
        ) {
            this.isTokenNot = isTokenNot;
            this.inversion = inversion;
        }
    }
}

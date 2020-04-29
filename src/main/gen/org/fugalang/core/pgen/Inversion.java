package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import org.fugalang.core.parser.DisjunctionRule;

// inversion: 'not' 'inversion' | 'comparison'
public final class Inversion extends DisjunctionRule {
    private final Inversion1 inversion1;
    private final Comparison comparison;

    public Inversion(
            Inversion1 inversion1,
            Comparison comparison
    ) {
        this.inversion1 = inversion1;
        this.comparison = comparison;
    }

    public Inversion1 getInversion1() {
        return inversion1;
    }

    public Comparison getComparison() {
        return comparison;
    }

    // 'not' 'inversion'
    public static final class Inversion1 extends ConjunctionRule {
        private final boolean isTokenNot;
        private final Inversion inversion;

        public Inversion1(
                boolean isTokenNot,
                Inversion inversion
        ) {
            this.isTokenNot = isTokenNot;
            this.inversion = inversion;
        }

        public boolean getIsTokenNot() {
            return isTokenNot;
        }

        public Inversion getInversion() {
            return inversion;
        }
    }
}

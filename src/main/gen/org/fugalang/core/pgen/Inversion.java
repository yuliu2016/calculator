package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import org.fugalang.core.parser.DisjunctionRule;

// inversion: 'not' 'inversion' | 'comparison'
public final class Inversion extends DisjunctionRule {
    public static final String RULE_NAME = "inversion";

    private final Inversion1 inversion1;
    private final Comparison comparison;

    public Inversion(
            Inversion1 inversion1,
            Comparison comparison
    ) {
        this.inversion1 = inversion1;
        this.comparison = comparison;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addChoice("inversion1", inversion1);
        addChoice("comparison", comparison);
    }

    public Inversion1 inversion1() {
        return inversion1;
    }

    public Comparison comparison() {
        return comparison;
    }

    // 'not' 'inversion'
    public static final class Inversion1 extends ConjunctionRule {
        public static final String RULE_NAME = "inversion:1";

        private final boolean isTokenNot;
        private final Inversion inversion;

        public Inversion1(
                boolean isTokenNot,
                Inversion inversion
        ) {
            this.isTokenNot = isTokenNot;
            this.inversion = inversion;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("isTokenNot", isTokenNot);
            addRequired("inversion", inversion);
        }

        public boolean isTokenNot() {
            return isTokenNot;
        }

        public Inversion inversion() {
            return inversion;
        }
    }
}

package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * inversion: 'not' 'inversion' | 'comparison'
 */
public final class Inversion extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("inversion", RuleType.Disjunction);

    public static Inversion of(ParseTreeNode node) {
        return new Inversion(node);
    }

    private Inversion(ParseTreeNode node) {
        super(RULE, node);
    }

    public Inversion1 notInversion() {
        return get(0, Inversion1::of);
    }

    public boolean hasNotInversion() {
        return has(0, Inversion1.RULE);
    }

    public Comparison comparison() {
        return get(1, Comparison::of);
    }

    public boolean hasComparison() {
        return has(1, Comparison.RULE);
    }

    /**
     * 'not' 'inversion'
     */
    public static final class Inversion1 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("inversion:1", RuleType.Conjunction);

        public static Inversion1 of(ParseTreeNode node) {
            return new Inversion1(node);
        }

        private Inversion1(ParseTreeNode node) {
            super(RULE, node);
        }

        public Inversion inversion() {
            return get(1, Inversion::of);
        }
    }
}

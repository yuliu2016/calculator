package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * factor: ('+' | '-' | '~') 'factor' | 'power'
 */
public final class Factor extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("factor", RuleType.Disjunction);

    public static Factor of(ParseTreeNode node) {
        return new Factor(node);
    }

    private Factor(ParseTreeNode node) {
        super(RULE, node);
    }

    public Factor1 factor1() {
        return get(0, Factor1::of);
    }

    public boolean hasFactor1() {
        return has(0, Factor1.RULE);
    }

    public Power power() {
        return get(1, Power::of);
    }

    public boolean hasPower() {
        return has(1, Power.RULE);
    }

    /**
     * ('+' | '-' | '~') 'factor'
     */
    public static final class Factor1 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("factor:1", RuleType.Conjunction);

        public static Factor1 of(ParseTreeNode node) {
            return new Factor1(node);
        }

        private Factor1(ParseTreeNode node) {
            super(RULE, node);
        }

        public Factor11 factor11() {
            return get(0, Factor11::of);
        }

        public Factor factor() {
            return get(1, Factor::of);
        }
    }

    /**
     * '+' | '-' | '~'
     */
    public static final class Factor11 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("factor:1:1", RuleType.Disjunction);

        public static Factor11 of(ParseTreeNode node) {
            return new Factor11(node);
        }

        private Factor11(ParseTreeNode node) {
            super(RULE, node);
        }

        public boolean isPlus() {
            return is(0);
        }

        public boolean isMinus() {
            return is(1);
        }

        public boolean isBitNot() {
            return is(2);
        }
    }
}

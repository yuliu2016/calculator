package org.fugalang.core.calculator.pgen;

import org.fugalang.core.parser.*;

/**
 * power: 'atom' ['**' 'factor']
 */
public final class Power extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("power", RuleType.Conjunction);

    public static Power of(ParseTreeNode node) {
        return new Power(node);
    }

    private Power(ParseTreeNode node) {
        super(RULE, node);
    }

    public Atom atom() {
        return get(0, Atom::of);
    }

    public Power2 factor() {
        return get(1, Power2::of);
    }

    public boolean hasFactor() {
        return has(1, Power2.RULE);
    }

    /**
     * '**' 'factor'
     */
    public static final class Power2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("power:2", RuleType.Conjunction);

        public static Power2 of(ParseTreeNode node) {
            return new Power2(node);
        }

        private Power2(ParseTreeNode node) {
            super(RULE, node);
        }

        public Factor factor() {
            return get(1, Factor::of);
        }
    }
}

package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

/**
 * factor: ('+' | '-' | '~') 'factor' | 'power'
 */
public final class Factor extends NodeWrapper {

    public Factor(ParseTreeNode node) {
        super(ParserRules.FACTOR, node);
    }

    public Factor1 factor1() {
        return get(0, Factor1.class);
    }

    public boolean hasFactor1() {
        return has(0);
    }

    public Power power() {
        return get(1, Power.class);
    }

    public boolean hasPower() {
        return has(1);
    }

    /**
     * ('+' | '-' | '~') 'factor'
     */
    public static final class Factor1 extends NodeWrapper {

        public Factor1(ParseTreeNode node) {
            super(ParserRules.FACTOR_1, node);
        }

        public Factor11 factor11() {
            return get(0, Factor11.class);
        }

        public Factor factor() {
            return get(1, Factor.class);
        }
    }

    /**
     * '+' | '-' | '~'
     */
    public static final class Factor11 extends NodeWrapper {

        public Factor11(ParseTreeNode node) {
            super(ParserRules.FACTOR_1_1, node);
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

package org.fugalang.core.calculator.pgen;

import org.fugalang.core.calculator.pgen.parser.ParserRules;
import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * power: 'atom' ['**' 'factor']
 */
public final class Power extends NodeWrapper {

    public Power(ParseTreeNode node) {
        super(ParserRules.POWER, node);
    }

    public Atom atom() {
        return get(0, Atom::new);
    }

    public Power2 factor() {
        return get(1, Power2::new);
    }

    public boolean hasFactor() {
        return has(1, ParserRules.POWER_2);
    }

    /**
     * '**' 'factor'
     */
    public static final class Power2 extends NodeWrapper {

        public Power2(ParseTreeNode node) {
            super(ParserRules.POWER_2, node);
        }

        public Factor factor() {
            return get(1, Factor::new);
        }
    }
}

package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

/**
 * power: 'primary' ['**' 'factor']
 */
public final class Power extends NodeWrapper {

    public Power(ParseTreeNode node) {
        super(ParserRules.POWER, node);
    }

    public Primary primary() {
        return get(0, Primary.class);
    }

    public Power2 factor() {
        return get(1, Power2.class);
    }

    public boolean hasFactor() {
        return has(1);
    }

    /**
     * '**' 'factor'
     */
    public static final class Power2 extends NodeWrapper {

        public Power2(ParseTreeNode node) {
            super(ParserRules.POWER_2, node);
        }

        public Factor factor() {
            return get(1, Factor.class);
        }
    }
}

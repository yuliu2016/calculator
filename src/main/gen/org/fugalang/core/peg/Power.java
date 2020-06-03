package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * power: primary '**' factor | primary
 */
public final class Power extends NodeWrapper {

    public Power(ParseTreeNode node) {
        super(node);
    }

    public Power1 primaryPowerFactor() {
        return new Power1(get(0));
    }

    public boolean hasPrimaryPowerFactor() {
        return has(0);
    }

    public Primary primary() {
        return new Primary(get(1));
    }

    public boolean hasPrimary() {
        return has(1);
    }

    /**
     * primary '**' factor
     */
    public static final class Power1 extends NodeWrapper {

        public Power1(ParseTreeNode node) {
            super(node);
        }

        public Primary primary() {
            return new Primary(get(0));
        }

        public Factor factor() {
            return new Factor(get(2));
        }
    }
}

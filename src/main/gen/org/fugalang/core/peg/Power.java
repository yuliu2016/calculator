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
        return get(0, Power1.class);
    }

    public boolean hasPrimaryPowerFactor() {
        return has(0);
    }

    public Primary primary() {
        return get(1, Primary.class);
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
            return get(0, Primary.class);
        }

        public Factor factor() {
            return get(2, Factor.class);
        }
    }
}

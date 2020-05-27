package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * factor: '+' factor | '-' factor | '~' factor | power
 */
public final class Factor extends NodeWrapper {

    public Factor(ParseTreeNode node) {
        super(node);
    }

    public Factor1 plusFactor() {
        return get(0, Factor1.class);
    }

    public boolean hasPlusFactor() {
        return has(0);
    }

    public Factor2 minusFactor() {
        return get(1, Factor2.class);
    }

    public boolean hasMinusFactor() {
        return has(1);
    }

    public Factor3 bitNotFactor() {
        return get(2, Factor3.class);
    }

    public boolean hasBitNotFactor() {
        return has(2);
    }

    public Power power() {
        return get(3, Power.class);
    }

    public boolean hasPower() {
        return has(3);
    }

    /**
     * '+' factor
     */
    public static final class Factor1 extends NodeWrapper {

        public Factor1(ParseTreeNode node) {
            super(node);
        }

        public Factor factor() {
            return get(1, Factor.class);
        }
    }

    /**
     * '-' factor
     */
    public static final class Factor2 extends NodeWrapper {

        public Factor2(ParseTreeNode node) {
            super(node);
        }

        public Factor factor() {
            return get(1, Factor.class);
        }
    }

    /**
     * '~' factor
     */
    public static final class Factor3 extends NodeWrapper {

        public Factor3(ParseTreeNode node) {
            super(node);
        }

        public Factor factor() {
            return get(1, Factor.class);
        }
    }
}

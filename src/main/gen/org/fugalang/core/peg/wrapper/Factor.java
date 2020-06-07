package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * factor:
 * *   | '+' factor
 * *   | '-' factor
 * *   | '~' factor
 * *   | power
 */
public final class Factor extends NodeWrapper {

    public Factor(ParseTreeNode node) {
        super(node);
    }

    public Factor1 plusFactor() {
        return new Factor1(get(0));
    }

    public boolean hasPlusFactor() {
        return has(0);
    }

    public Factor2 minusFactor() {
        return new Factor2(get(1));
    }

    public boolean hasMinusFactor() {
        return has(1);
    }

    public Factor3 bitNotFactor() {
        return new Factor3(get(2));
    }

    public boolean hasBitNotFactor() {
        return has(2);
    }

    public Power power() {
        return new Power(get(3));
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
            return new Factor(get(1));
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
            return new Factor(get(1));
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
            return new Factor(get(1));
        }
    }
}

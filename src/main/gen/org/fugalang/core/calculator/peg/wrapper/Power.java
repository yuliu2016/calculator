package org.fugalang.core.calculator.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * power:
 * *   | atom '**' factor
 * *   | atom
 */
public final class Power extends NodeWrapper {

    public Power(ParseTreeNode node) {
        super(node);
    }

    public Power1 atomPowerFactor() {
        return new Power1(get(0));
    }

    public boolean hasAtomPowerFactor() {
        return has(0);
    }

    public Atom atom() {
        return new Atom(get(1));
    }

    public boolean hasAtom() {
        return has(1);
    }

    /**
     * atom '**' factor
     */
    public static final class Power1 extends NodeWrapper {

        public Power1(ParseTreeNode node) {
            super(node);
        }

        public Atom atom() {
            return new Atom(get(0));
        }

        public Factor factor() {
            return new Factor(get(2));
        }
    }
}

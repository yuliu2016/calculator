package org.fugalang.core.calculator.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * power: atom '**' factor | atom
 */
public final class Power extends NodeWrapper {

    public Power(ParseTreeNode node) {
        super(node);
    }

    public Power1 atomPowerFactor() {
        return get(0, Power1.class);
    }

    public boolean hasAtomPowerFactor() {
        return has(0);
    }

    public Atom atom() {
        return get(1, Atom.class);
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
            return get(0, Atom.class);
        }

        public Factor factor() {
            return get(2, Factor.class);
        }
    }
}

package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * inversion: 'not' inversion | comparison
 */
public final class Inversion extends NodeWrapper {

    public Inversion(ParseTreeNode node) {
        super(node);
    }

    public Inversion1 notInversion() {
        return new Inversion1(get(0));
    }

    public boolean hasNotInversion() {
        return has(0);
    }

    public Comparison comparison() {
        return new Comparison(get(1));
    }

    public boolean hasComparison() {
        return has(1);
    }

    /**
     * 'not' inversion
     */
    public static final class Inversion1 extends NodeWrapper {

        public Inversion1(ParseTreeNode node) {
            super(node);
        }

        public Inversion inversion() {
            return new Inversion(get(1));
        }
    }
}

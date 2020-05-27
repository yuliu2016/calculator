package org.fugalang.core.peg;

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
        return get(0, Inversion1.class);
    }

    public boolean hasNotInversion() {
        return has(0);
    }

    public Comparison comparison() {
        return get(1, Comparison.class);
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
            return get(1, Inversion.class);
        }
    }
}

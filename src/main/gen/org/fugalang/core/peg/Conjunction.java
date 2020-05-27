package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * conjunction: conjunction 'and' inversion | inversion
 */
public final class Conjunction extends NodeWrapper {

    public Conjunction(ParseTreeNode node) {
        super(node);
    }

    public Conjunction1 conjunctionAndInversion() {
        return get(0, Conjunction1.class);
    }

    public boolean hasConjunctionAndInversion() {
        return has(0);
    }

    public Inversion inversion() {
        return get(1, Inversion.class);
    }

    public boolean hasInversion() {
        return has(1);
    }

    /**
     * conjunction 'and' inversion
     */
    public static final class Conjunction1 extends NodeWrapper {

        public Conjunction1(ParseTreeNode node) {
            super(node);
        }

        public Conjunction conjunction() {
            return get(0, Conjunction.class);
        }

        public Inversion inversion() {
            return get(2, Inversion.class);
        }
    }
}

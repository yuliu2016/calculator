package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * conjunction:
 * *   | conjunction 'and' inversion
 * *   | inversion
 */
public final class Conjunction extends NodeWrapper {

    public Conjunction(ParseTreeNode node) {
        super(node);
    }

    public Conjunction1 conjunctionAndInversion() {
        return new Conjunction1(get(0));
    }

    public boolean hasConjunctionAndInversion() {
        return has(0);
    }

    public Inversion inversion() {
        return new Inversion(get(1));
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
            return new Conjunction(get(0));
        }

        public Inversion inversion() {
            return new Inversion(get(2));
        }
    }
}

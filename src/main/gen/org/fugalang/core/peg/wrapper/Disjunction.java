package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * disjunction: disjunction 'or' conjunction | conjunction
 */
public final class Disjunction extends NodeWrapper {

    public Disjunction(ParseTreeNode node) {
        super(node);
    }

    public Disjunction1 disjunctionOrConjunction() {
        return new Disjunction1(get(0));
    }

    public boolean hasDisjunctionOrConjunction() {
        return has(0);
    }

    public Conjunction conjunction() {
        return new Conjunction(get(1));
    }

    public boolean hasConjunction() {
        return has(1);
    }

    /**
     * disjunction 'or' conjunction
     */
    public static final class Disjunction1 extends NodeWrapper {

        public Disjunction1(ParseTreeNode node) {
            super(node);
        }

        public Disjunction disjunction() {
            return new Disjunction(get(0));
        }

        public Conjunction conjunction() {
            return new Conjunction(get(2));
        }
    }
}

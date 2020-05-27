package org.fugalang.core.peg;

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
        return get(0, Disjunction1.class);
    }

    public boolean hasDisjunctionOrConjunction() {
        return has(0);
    }

    public Conjunction conjunction() {
        return get(1, Conjunction.class);
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
            return get(0, Disjunction.class);
        }

        public Conjunction conjunction() {
            return get(2, Conjunction.class);
        }
    }
}

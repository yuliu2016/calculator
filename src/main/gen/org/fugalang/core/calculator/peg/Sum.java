package org.fugalang.core.calculator.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * sum: sum '+' term | sum '-' term | term
 */
public final class Sum extends NodeWrapper {

    public Sum(ParseTreeNode node) {
        super(node);
    }

    public Sum1 sumPlusTerm() {
        return get(0, Sum1.class);
    }

    public boolean hasSumPlusTerm() {
        return has(0);
    }

    public Sum2 sumMinusTerm() {
        return get(1, Sum2.class);
    }

    public boolean hasSumMinusTerm() {
        return has(1);
    }

    public Term term() {
        return get(2, Term.class);
    }

    public boolean hasTerm() {
        return has(2);
    }

    /**
     * sum '+' term
     */
    public static final class Sum1 extends NodeWrapper {

        public Sum1(ParseTreeNode node) {
            super(node);
        }

        public Sum sum() {
            return get(0, Sum.class);
        }

        public Term term() {
            return get(2, Term.class);
        }
    }

    /**
     * sum '-' term
     */
    public static final class Sum2 extends NodeWrapper {

        public Sum2(ParseTreeNode node) {
            super(node);
        }

        public Sum sum() {
            return get(0, Sum.class);
        }

        public Term term() {
            return get(2, Term.class);
        }
    }
}

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
        return new Sum1(get(0));
    }

    public boolean hasSumPlusTerm() {
        return has(0);
    }

    public Sum2 sumMinusTerm() {
        return new Sum2(get(1));
    }

    public boolean hasSumMinusTerm() {
        return has(1);
    }

    public Term term() {
        return new Term(get(2));
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
            return new Sum(get(0));
        }

        public Term term() {
            return new Term(get(2));
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
            return new Sum(get(0));
        }

        public Term term() {
            return new Term(get(2));
        }
    }
}

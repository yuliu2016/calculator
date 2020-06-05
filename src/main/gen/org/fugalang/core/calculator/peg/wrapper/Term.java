package org.fugalang.core.calculator.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * term: term '*' factor | term '/' factor | term '%' factor | factor
 */
public final class Term extends NodeWrapper {

    public Term(ParseTreeNode node) {
        super(node);
    }

    public Term1 termTimesFactor() {
        return new Term1(get(0));
    }

    public boolean hasTermTimesFactor() {
        return has(0);
    }

    public Term2 termDivFactor() {
        return new Term2(get(1));
    }

    public boolean hasTermDivFactor() {
        return has(1);
    }

    public Term3 termModulusFactor() {
        return new Term3(get(2));
    }

    public boolean hasTermModulusFactor() {
        return has(2);
    }

    public Factor factor() {
        return new Factor(get(3));
    }

    public boolean hasFactor() {
        return has(3);
    }

    /**
     * term '*' factor
     */
    public static final class Term1 extends NodeWrapper {

        public Term1(ParseTreeNode node) {
            super(node);
        }

        public Term term() {
            return new Term(get(0));
        }

        public Factor factor() {
            return new Factor(get(2));
        }
    }

    /**
     * term '/' factor
     */
    public static final class Term2 extends NodeWrapper {

        public Term2(ParseTreeNode node) {
            super(node);
        }

        public Term term() {
            return new Term(get(0));
        }

        public Factor factor() {
            return new Factor(get(2));
        }
    }

    /**
     * term '%' factor
     */
    public static final class Term3 extends NodeWrapper {

        public Term3(ParseTreeNode node) {
            super(node);
        }

        public Term term() {
            return new Term(get(0));
        }

        public Factor factor() {
            return new Factor(get(2));
        }
    }
}

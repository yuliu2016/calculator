package org.fugalang.core.calculator.pgen;

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
        return get(0, Term1.class);
    }

    public boolean hasTermTimesFactor() {
        return has(0);
    }

    public Term2 termDivFactor() {
        return get(1, Term2.class);
    }

    public boolean hasTermDivFactor() {
        return has(1);
    }

    public Term3 termModulusFactor() {
        return get(2, Term3.class);
    }

    public boolean hasTermModulusFactor() {
        return has(2);
    }

    public Factor factor() {
        return get(3, Factor.class);
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
            return get(0, Term.class);
        }

        public Factor factor() {
            return get(2, Factor.class);
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
            return get(0, Term.class);
        }

        public Factor factor() {
            return get(2, Factor.class);
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
            return get(0, Term.class);
        }

        public Factor factor() {
            return get(2, Factor.class);
        }
    }
}

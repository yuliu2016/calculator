package org.fugalang.core.calculator.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * term: 'factor' (('*' | '/' | '%') 'factor')*
 */
public final class Term extends NodeWrapper {

    public Term(ParseTreeNode node) {
        super(node);
    }

    public Factor factor() {
        return get(0, Factor.class);
    }

    public List<Term2> term2s() {
        return getList(1, Term2.class);
    }

    /**
     * ('*' | '/' | '%') 'factor'
     */
    public static final class Term2 extends NodeWrapper {

        public Term2(ParseTreeNode node) {
            super(node);
        }

        public Term21 term21() {
            return get(0, Term21.class);
        }

        public Factor factor() {
            return get(1, Factor.class);
        }
    }

    /**
     * '*' | '/' | '%'
     */
    public static final class Term21 extends NodeWrapper {

        public Term21(ParseTreeNode node) {
            super(node);
        }

        public boolean isTimes() {
            return is(0);
        }

        public boolean isDiv() {
            return is(1);
        }

        public boolean isModulus() {
            return is(2);
        }
    }
}

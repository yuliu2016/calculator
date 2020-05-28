package org.fugalang.core.calculator.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * atom: '(' sum ')' | NAME '(' [parameters] ')' | NAME | NUMBER
 */
public final class Atom extends NodeWrapper {

    public Atom(ParseTreeNode node) {
        super(node);
    }

    public Atom1 lparSumRpar() {
        return get(0, Atom1.class);
    }

    public boolean hasLparSumRpar() {
        return has(0);
    }

    public Atom2 atom2() {
        return get(1, Atom2.class);
    }

    public boolean hasAtom2() {
        return has(1);
    }

    public String name() {
        return get(2, TokenType.NAME);
    }

    public boolean hasName() {
        return has(2);
    }

    public String number() {
        return get(3, TokenType.NUMBER);
    }

    public boolean hasNumber() {
        return has(3);
    }

    /**
     * '(' sum ')'
     */
    public static final class Atom1 extends NodeWrapper {

        public Atom1(ParseTreeNode node) {
            super(node);
        }

        public Sum sum() {
            return get(1, Sum.class);
        }
    }

    /**
     * NAME '(' [parameters] ')'
     */
    public static final class Atom2 extends NodeWrapper {

        public Atom2(ParseTreeNode node) {
            super(node);
        }

        public String name() {
            return get(0, TokenType.NAME);
        }

        public Parameters parameters() {
            return get(2, Parameters.class);
        }

        public boolean hasParameters() {
            return has(2);
        }
    }
}

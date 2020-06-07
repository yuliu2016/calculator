package org.fugalang.core.calculator.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * atom:
 * *   | '(' sum ')'
 * *   | NAME '(' [parameters] ')'
 * *   | NAME
 * *   | NUMBER
 */
public final class Atom extends NodeWrapper {

    public Atom(ParseTreeNode node) {
        super(node);
    }

    public Atom1 lparSumRpar() {
        return new Atom1(get(0));
    }

    public boolean hasLparSumRpar() {
        return has(0);
    }

    public Atom2 atom2() {
        return new Atom2(get(1));
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
            return new Sum(get(1));
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
            return new Parameters(get(2));
        }

        public boolean hasParameters() {
            return has(2);
        }
    }
}

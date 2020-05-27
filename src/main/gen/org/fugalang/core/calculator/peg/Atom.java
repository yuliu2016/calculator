package org.fugalang.core.calculator.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * atom: '(' sum ')' | NUMBER
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

    public String number() {
        return get(1, TokenType.NUMBER);
    }

    public boolean hasNumber() {
        return has(1);
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
}

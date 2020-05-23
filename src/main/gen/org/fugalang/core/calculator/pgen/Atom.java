package org.fugalang.core.calculator.pgen;

import org.fugalang.core.calculator.pgen.parser.ParserRules;
import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * atom: '(' 'sum' ')' | 'NUMBER'
 */
public final class Atom extends NodeWrapper {

    public Atom(ParseTreeNode node) {
        super(ParserRules.ATOM, node);
    }

    public Atom1 sum() {
        return get(0, Atom1.class);
    }

    public boolean hasSum() {
        return has(0);
    }

    public String number() {
        return get(1, TokenType.NUMBER);
    }

    public boolean hasNumber() {
        return has(1);
    }

    /**
     * '(' 'sum' ')'
     */
    public static final class Atom1 extends NodeWrapper {

        public Atom1(ParseTreeNode node) {
            super(ParserRules.ATOM_1, node);
        }

        public Sum sum() {
            return get(1, Sum.class);
        }
    }
}

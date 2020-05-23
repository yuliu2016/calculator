package org.fugalang.core.calculator.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * atom: '(' 'sum' ')' | 'NUMBER'
 */
public final class Atom extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("atom", RuleType.Disjunction);

    public static Atom of(ParseTreeNode node) {
        return new Atom(node);
    }

    private Atom(ParseTreeNode node) {
        super(RULE, node);
    }

    public Atom1 sum() {
        return get(0, Atom1::of);
    }

    public boolean hasSum() {
        return has(0, Atom1.RULE);
    }

    public String number() {
        return get(1, TokenType.NUMBER);
    }

    public boolean hasNumber() {
        return has(1, TokenType.NUMBER);
    }

    /**
     * '(' 'sum' ')'
     */
    public static final class Atom1 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("atom:1", RuleType.Conjunction);

        public static Atom1 of(ParseTreeNode node) {
            return new Atom1(node);
        }

        private Atom1(ParseTreeNode node) {
            super(RULE, node);
        }

        public Sum sum() {
            return get(1, Sum::of);
        }
    }
}

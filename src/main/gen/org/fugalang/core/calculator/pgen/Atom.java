package org.fugalang.core.calculator.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * atom: '(' 'sum' ')' | 'NUMBER'
 */
public final class Atom extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("atom", RuleType.Disjunction, true);

    public static Atom of(ParseTreeNode node) {
        return new Atom(node);
    }

    private Atom(ParseTreeNode node) {
        super(RULE, node);
    }

    public Atom1 atom1() {
        return Atom1.of(getItem(0));
    }

    public boolean hasAtom1() {
        return hasItemOfRule(0, Atom1.RULE);
    }

    public String number() {
        return getItemOfType(1, TokenType.NUMBER);
    }

    public boolean hasNumber() {
        return hasItemOfType(1, TokenType.NUMBER);
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = Atom1.parse(t, l + 1);
        r = r || t.consumeToken(TokenType.NUMBER);
        t.exit(r);
        return r;
    }

    /**
     * '(' 'sum' ')'
     */
    public static final class Atom1 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("atom:1", RuleType.Conjunction, false);

        public static Atom1 of(ParseTreeNode node) {
            return new Atom1(node);
        }

        private Atom1(ParseTreeNode node) {
            super(RULE, node);
        }

        public Sum sum() {
            return Sum.of(getItem(1));
        }

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = t.consumeToken("(");
            r = r && Sum.parse(t, l + 1);
            r = r && t.consumeToken(")");
            t.exit(r);
            return r;
        }
    }
}

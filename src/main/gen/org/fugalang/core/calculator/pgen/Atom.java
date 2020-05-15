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

    public Atom1 atom1() {
        return Atom1.of(get(0));
    }

    public boolean hasAtom1() {
        return has(0, Atom1.RULE);
    }

    public String number() {
        return get(1, TokenType.NUMBER);
    }

    public boolean hasNumber() {
        return has(1, TokenType.NUMBER);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = Atom1.parse(t, lv + 1);
        r = r || t.consume(TokenType.NUMBER);
        t.exit(r);
        return r;
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
            return Sum.of(get(1));
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume("(");
            r = r && Sum.parse(t, lv + 1);
            r = r && t.consume(")");
            t.exit(r);
            return r;
        }
    }
}

package org.fugalang.core.calculator.pgen.parser;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.token.TokenType;

import static org.fugalang.core.calculator.pgen.parser.ParserRules.*;

@SuppressWarnings("UnusedReturnValue")
public class Parser {

    /**
     * sum: 'term' (('+' | '-') 'term')*
     */
    public static boolean sum(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, SUM);
        boolean r;
        r = term(t, lv + 1);
        if (r) sum_2_loop(t, lv);
        t.exit(r);
        return r;
    }

    private static void sum_2_loop(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!sum_2(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ('+' | '-') 'term'
     */
    public static boolean sum_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, SUM_2);
        boolean r;
        r = sum_2_1(t, lv + 1);
        r = r && term(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * '+' | '-'
     */
    public static boolean sum_2_1(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, SUM_2_1);
        boolean r;
        r = t.consume("+");
        r = r || t.consume("-");
        t.exit(r);
        return r;
    }

    /**
     * term: 'factor' (('*' | '/' | '%') 'factor')*
     */
    public static boolean term(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, TERM);
        boolean r;
        r = factor(t, lv + 1);
        if (r) term_2_loop(t, lv);
        t.exit(r);
        return r;
    }

    private static void term_2_loop(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!term_2(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ('*' | '/' | '%') 'factor'
     */
    public static boolean term_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, TERM_2);
        boolean r;
        r = term_2_1(t, lv + 1);
        r = r && factor(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * '*' | '/' | '%'
     */
    public static boolean term_2_1(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, TERM_2_1);
        boolean r;
        r = t.consume("*");
        r = r || t.consume("/");
        r = r || t.consume("%");
        t.exit(r);
        return r;
    }

    /**
     * factor: ('+' | '-' | '~') 'factor' | 'power'
     */
    public static boolean factor(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, FACTOR);
        boolean r;
        r = factor_1(t, lv + 1);
        r = r || power(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * ('+' | '-' | '~') 'factor'
     */
    public static boolean factor_1(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, FACTOR_1);
        boolean r;
        r = factor_1_1(t, lv + 1);
        r = r && factor(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * '+' | '-' | '~'
     */
    public static boolean factor_1_1(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, FACTOR_1_1);
        boolean r;
        r = t.consume("+");
        r = r || t.consume("-");
        r = r || t.consume("~");
        t.exit(r);
        return r;
    }

    /**
     * power: 'atom' ['**' 'factor']
     */
    public static boolean power(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, POWER);
        boolean r;
        r = atom(t, lv + 1);
        if (r) power_2(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * '**' 'factor'
     */
    public static boolean power_2(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, POWER_2);
        boolean r;
        r = t.consume("**");
        r = r && factor(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * atom: '(' 'sum' ')' | 'NUMBER'
     */
    public static boolean atom(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, ATOM);
        boolean r;
        r = atom_1(t, lv + 1);
        r = r || t.consume(TokenType.NUMBER);
        t.exit(r);
        return r;
    }

    /**
     * '(' 'sum' ')'
     */
    public static boolean atom_1(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, ATOM_1);
        boolean r;
        r = t.consume("(");
        r = r && sum(t, lv + 1);
        r = r && t.consume(")");
        t.exit(r);
        return r;
    }
}

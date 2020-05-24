package org.fugalang.core.calculator.pgen.parser;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.token.TokenType;

import static org.fugalang.core.calculator.pgen.parser.CalculatorRules.*;

@SuppressWarnings("UnusedReturnValue")
public class CalculatorParser {

    /**
     * sum: term (('+' | '-') term)*
     */
    public static boolean sum(ParseTree t, int lv) {
        var m = t.enter(lv, SUM);
        if (m != null) return m;
        boolean r;
        r = term(t, lv + 1);
        if (r) sum_2_loop(t, lv);
        t.exit(r);
        return r;
    }

    private static void sum_2_loop(ParseTree t, int lv) {
        t.enterLoop();
        while (true) {
            var p = t.position();
            if (!sum_2(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitLoop();
    }

    /**
     * ('+' | '-') term
     */
    private static boolean sum_2(ParseTree t, int lv) {
        var m = t.enter(lv, SUM_2);
        if (m != null) return m;
        boolean r;
        r = sum_2_1(t, lv + 1);
        r = r && term(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * '+' | '-'
     */
    private static boolean sum_2_1(ParseTree t, int lv) {
        var m = t.enter(lv, SUM_2_1);
        if (m != null) return m;
        boolean r;
        r = t.consume("+");
        r = r || t.consume("-");
        t.exit(r);
        return r;
    }

    /**
     * term: factor (('*' | '/' | '%') factor)*
     */
    public static boolean term(ParseTree t, int lv) {
        var m = t.enter(lv, TERM);
        if (m != null) return m;
        boolean r;
        r = factor(t, lv + 1);
        if (r) term_2_loop(t, lv);
        t.exit(r);
        return r;
    }

    private static void term_2_loop(ParseTree t, int lv) {
        t.enterLoop();
        while (true) {
            var p = t.position();
            if (!term_2(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitLoop();
    }

    /**
     * ('*' | '/' | '%') factor
     */
    private static boolean term_2(ParseTree t, int lv) {
        var m = t.enter(lv, TERM_2);
        if (m != null) return m;
        boolean r;
        r = term_2_1(t, lv + 1);
        r = r && factor(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * '*' | '/' | '%'
     */
    private static boolean term_2_1(ParseTree t, int lv) {
        var m = t.enter(lv, TERM_2_1);
        if (m != null) return m;
        boolean r;
        r = t.consume("*");
        r = r || t.consume("/");
        r = r || t.consume("%");
        t.exit(r);
        return r;
    }

    /**
     * factor: ('+' | '-' | '~') factor | power
     */
    public static boolean factor(ParseTree t, int lv) {
        var m = t.enter(lv, FACTOR);
        if (m != null) return m;
        boolean r;
        r = factor_1(t, lv + 1);
        r = r || power(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * ('+' | '-' | '~') factor
     */
    private static boolean factor_1(ParseTree t, int lv) {
        var m = t.enter(lv, FACTOR_1);
        if (m != null) return m;
        boolean r;
        r = factor_1_1(t, lv + 1);
        r = r && factor(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * '+' | '-' | '~'
     */
    private static boolean factor_1_1(ParseTree t, int lv) {
        var m = t.enter(lv, FACTOR_1_1);
        if (m != null) return m;
        boolean r;
        r = t.consume("+");
        r = r || t.consume("-");
        r = r || t.consume("~");
        t.exit(r);
        return r;
    }

    /**
     * power: atom '**' factor | atom
     */
    public static boolean power(ParseTree t, int lv) {
        var m = t.enter(lv, POWER);
        if (m != null) return m;
        boolean r;
        r = power_1(t, lv + 1);
        r = r || atom(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * atom '**' factor
     */
    private static boolean power_1(ParseTree t, int lv) {
        var m = t.enter(lv, POWER_1);
        if (m != null) return m;
        boolean r;
        r = atom(t, lv + 1);
        r = r && t.consume("**");
        r = r && factor(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * atom: '(' sum ')' | NUMBER
     */
    public static boolean atom(ParseTree t, int lv) {
        var m = t.enter(lv, ATOM);
        if (m != null) return m;
        boolean r;
        r = atom_1(t, lv + 1);
        r = r || t.consume(TokenType.NUMBER);
        t.exit(r);
        return r;
    }

    /**
     * '(' sum ')'
     */
    private static boolean atom_1(ParseTree t, int lv) {
        var m = t.enter(lv, ATOM_1);
        if (m != null) return m;
        boolean r;
        r = t.consume("(");
        r = r && sum(t, lv + 1);
        r = r && t.consume(")");
        t.exit(r);
        return r;
    }
}

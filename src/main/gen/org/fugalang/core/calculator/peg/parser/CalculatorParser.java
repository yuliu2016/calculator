package org.fugalang.core.calculator.peg.parser;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.token.TokenType;

import static org.fugalang.core.calculator.peg.parser.CalculatorRules.*;

@SuppressWarnings("UnusedReturnValue")
public class CalculatorParser {

    /**
     * sum:
     * *   | sum '+' term
     * *   | sum '-' term
     * *   | term
     */
    public static boolean sum(ParseTree t) {
        var m = t.enter(SUM);
        if (m != null) return m;
        var p = t.position();
        boolean s = false;
        while (true) {
            t.cache(s);
            boolean r;
            r = sum_1(t);
            r = r || sum_2(t);
            r = r || term(t);
            s = r || s;
            var e = t.position();
            if (e <= p) break;
            p = e;
        }
        t.restore(p);
        t.exit(s);
        return s;
    }

    /**
     * sum '+' term
     */
    private static boolean sum_1(ParseTree t) {
        var m = t.enter(SUM_1);
        if (m != null) return m;
        boolean r;
        r = sum(t);
        r = r && t.consume("+");
        r = r && term(t);
        t.exit(r);
        return r;
    }

    /**
     * sum '-' term
     */
    private static boolean sum_2(ParseTree t) {
        var m = t.enter(SUM_2);
        if (m != null) return m;
        boolean r;
        r = sum(t);
        r = r && t.consume("-");
        r = r && term(t);
        t.exit(r);
        return r;
    }

    /**
     * term:
     * *   | term '*' factor
     * *   | term '/' factor
     * *   | term '%' factor
     * *   | factor
     */
    public static boolean term(ParseTree t) {
        var m = t.enter(TERM);
        if (m != null) return m;
        var p = t.position();
        boolean s = false;
        while (true) {
            t.cache(s);
            boolean r;
            r = term_1(t);
            r = r || term_2(t);
            r = r || term_3(t);
            r = r || factor(t);
            s = r || s;
            var e = t.position();
            if (e <= p) break;
            p = e;
        }
        t.restore(p);
        t.exit(s);
        return s;
    }

    /**
     * term '*' factor
     */
    private static boolean term_1(ParseTree t) {
        var m = t.enter(TERM_1);
        if (m != null) return m;
        boolean r;
        r = term(t);
        r = r && t.consume("*");
        r = r && factor(t);
        t.exit(r);
        return r;
    }

    /**
     * term '/' factor
     */
    private static boolean term_2(ParseTree t) {
        var m = t.enter(TERM_2);
        if (m != null) return m;
        boolean r;
        r = term(t);
        r = r && t.consume("/");
        r = r && factor(t);
        t.exit(r);
        return r;
    }

    /**
     * term '%' factor
     */
    private static boolean term_3(ParseTree t) {
        var m = t.enter(TERM_3);
        if (m != null) return m;
        boolean r;
        r = term(t);
        r = r && t.consume("%");
        r = r && factor(t);
        t.exit(r);
        return r;
    }

    /**
     * factor:
     * *   | '+' factor
     * *   | '-' factor
     * *   | '~' factor
     * *   | power
     */
    public static boolean factor(ParseTree t) {
        var m = t.enter(FACTOR);
        if (m != null) return m;
        boolean r;
        r = factor_1(t);
        r = r || factor_2(t);
        r = r || factor_3(t);
        r = r || power(t);
        t.exit(r);
        return r;
    }

    /**
     * '+' factor
     */
    private static boolean factor_1(ParseTree t) {
        var m = t.enter(FACTOR_1);
        if (m != null) return m;
        boolean r;
        r = t.consume("+");
        r = r && factor(t);
        t.exit(r);
        return r;
    }

    /**
     * '-' factor
     */
    private static boolean factor_2(ParseTree t) {
        var m = t.enter(FACTOR_2);
        if (m != null) return m;
        boolean r;
        r = t.consume("-");
        r = r && factor(t);
        t.exit(r);
        return r;
    }

    /**
     * '~' factor
     */
    private static boolean factor_3(ParseTree t) {
        var m = t.enter(FACTOR_3);
        if (m != null) return m;
        boolean r;
        r = t.consume("~");
        r = r && factor(t);
        t.exit(r);
        return r;
    }

    /**
     * power:
     * *   | atom '**' factor
     * *   | atom
     */
    public static boolean power(ParseTree t) {
        var m = t.enter(POWER);
        if (m != null) return m;
        boolean r;
        r = power_1(t);
        r = r || atom(t);
        t.exit(r);
        return r;
    }

    /**
     * atom '**' factor
     */
    private static boolean power_1(ParseTree t) {
        var m = t.enter(POWER_1);
        if (m != null) return m;
        boolean r;
        r = atom(t);
        r = r && t.consume("**");
        r = r && factor(t);
        t.exit(r);
        return r;
    }

    /**
     * atom:
     * *   | '(' sum ')'
     * *   | NAME '(' [parameters] ')'
     * *   | NAME
     * *   | NUMBER
     */
    public static boolean atom(ParseTree t) {
        var m = t.enter(ATOM);
        if (m != null) return m;
        boolean r;
        r = atom_1(t);
        r = r || atom_2(t);
        r = r || t.consume(TokenType.NAME);
        r = r || t.consume(TokenType.NUMBER);
        t.exit(r);
        return r;
    }

    /**
     * '(' sum ')'
     */
    private static boolean atom_1(ParseTree t) {
        var m = t.enter(ATOM_1);
        if (m != null) return m;
        boolean r;
        r = t.consume("(");
        r = r && sum(t);
        r = r && t.consume(")");
        t.exit(r);
        return r;
    }

    /**
     * NAME '(' [parameters] ')'
     */
    private static boolean atom_2(ParseTree t) {
        var m = t.enter(ATOM_2);
        if (m != null) return m;
        boolean r;
        r = t.consume(TokenType.NAME);
        r = r && t.consume("(");
        if (r) parameters(t);
        r = r && t.consume(")");
        t.exit(r);
        return r;
    }

    /**
     * parameters:
     * *   | ','.sum+ [',']
     */
    public static boolean parameters(ParseTree t) {
        var m = t.enter(PARAMETERS);
        if (m != null) return m;
        boolean r;
        r = sum_loop(t);
        if (r) t.consume(",");
        t.exit(r);
        return r;
    }

    private static boolean sum_loop(ParseTree t) {
        t.enterLoop();
        var r = sum(t);
        if (r) while (true) {
            if (!(t.skip(",") && sum(t))) break;
        }
        t.exitLoop();
        return r;
    }
}

package org.fugalang.grammar.peg.parser;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.token.TokenType;

import static org.fugalang.grammar.peg.parser.MetaRules.*;

@SuppressWarnings("UnusedReturnValue")
public class MetaParser {

    /**
     * grammar:
     * *   | [NEWLINE] rule+
     */
    public static boolean grammar(ParseTree t) {
        var m = t.enter(GRAMMAR);
        if (m != null) return m;
        boolean r;
        t.consume(TokenType.NEWLINE);
        r = rule_loop(t);
        t.exit(r);
        return r;
    }

    private static boolean rule_loop(ParseTree t) {
        t.enterLoop();
        var r = rule(t);
        if (r) while (true) {
            if (!rule(t)) break;
        }
        t.exitLoop();
        return r;
    }

    /**
     * rule:
     * *   | NAME [return_type] [rule_args] rule_suite
     */
    public static boolean rule(ParseTree t) {
        var m = t.enter(RULE);
        if (m != null) return m;
        boolean r;
        r = t.consume(TokenType.NAME);
        if (r) return_type(t);
        if (r) rule_args(t);
        r = r && rule_suite(t);
        t.exit(r);
        return r;
    }

    /**
     * return_type:
     * *   | '[' NAME ']'
     */
    public static boolean return_type(ParseTree t) {
        var m = t.enter(RETURN_TYPE);
        if (m != null) return m;
        boolean r;
        r = t.consume("[");
        r = r && t.consume(TokenType.NAME);
        r = r && t.consume("]");
        t.exit(r);
        return r;
    }

    /**
     * rule_args:
     * *   | '(' ','.rule_arg+ ')'
     */
    public static boolean rule_args(ParseTree t) {
        var m = t.enter(RULE_ARGS);
        if (m != null) return m;
        boolean r;
        r = t.consume("(");
        r = r && rule_arg_loop(t);
        r = r && t.consume(")");
        t.exit(r);
        return r;
    }

    private static boolean rule_arg_loop(ParseTree t) {
        t.enterLoop();
        var r = rule_arg(t);
        if (r) while (true) {
            var p = t.position();
            if (t.skip(",") && rule_arg(t)) continue;
            t.reset(p);
            break;
        }
        t.exitLoop();
        return r;
    }

    /**
     * rule_arg:
     * *   | NAME ['=' NAME]
     */
    public static boolean rule_arg(ParseTree t) {
        var m = t.enter(RULE_ARG);
        if (m != null) return m;
        boolean r;
        r = t.consume(TokenType.NAME);
        if (r) rule_arg_2(t);
        t.exit(r);
        return r;
    }

    /**
     * '=' NAME
     */
    private static boolean rule_arg_2(ParseTree t) {
        var m = t.enter(RULE_ARG_2);
        if (m != null) return m;
        boolean r;
        r = t.consume("=");
        r = r && t.consume(TokenType.NAME);
        t.exit(r);
        return r;
    }

    /**
     * rule_suite:
     * *   | ':' NEWLINE '|' alt_list NEWLINE
     */
    public static boolean rule_suite(ParseTree t) {
        var m = t.enter(RULE_SUITE);
        if (m != null) return m;
        boolean r;
        r = t.consume(":");
        r = r && t.consume(TokenType.NEWLINE);
        r = r && t.consume("|");
        r = r && alt_list(t);
        r = r && t.consume(TokenType.NEWLINE);
        t.exit(r);
        return r;
    }

    /**
     * alt_list:
     * *   | sequence alternative*
     */
    public static boolean alt_list(ParseTree t) {
        var m = t.enter(ALT_LIST);
        if (m != null) return m;
        boolean r;
        r = sequence(t);
        if (r) alternative_loop(t);
        t.exit(r);
        return r;
    }

    private static void alternative_loop(ParseTree t) {
        t.enterLoop();
        while (true) {
            if (!alternative(t)) break;
        }
        t.exitLoop();
    }

    /**
     * alternative:
     * *   | [NEWLINE] '|' sequence
     */
    public static boolean alternative(ParseTree t) {
        var m = t.enter(ALTERNATIVE);
        if (m != null) return m;
        boolean r;
        t.consume(TokenType.NEWLINE);
        r = t.consume("|");
        r = r && sequence(t);
        t.exit(r);
        return r;
    }

    /**
     * sequence:
     * *   | primary+
     */
    public static boolean sequence(ParseTree t) {
        var m = t.enter(SEQUENCE);
        if (m != null) return m;
        boolean r;
        r = primary_loop(t);
        t.exit(r);
        return r;
    }

    private static boolean primary_loop(ParseTree t) {
        t.enterLoop();
        var r = primary(t);
        if (r) while (true) {
            if (!primary(t)) break;
        }
        t.exitLoop();
        return r;
    }

    /**
     * primary:
     * *   | delimited
     * *   | '&' item
     * *   | '!' item
     * *   | item '*'
     * *   | item '+'
     * *   | item
     */
    public static boolean primary(ParseTree t) {
        var m = t.enter(PRIMARY);
        if (m != null) return m;
        boolean r;
        r = delimited(t);
        r = r || primary_2(t);
        r = r || primary_3(t);
        r = r || primary_4(t);
        r = r || primary_5(t);
        r = r || item(t);
        t.exit(r);
        return r;
    }

    /**
     * '&' item
     */
    private static boolean primary_2(ParseTree t) {
        var m = t.enter(PRIMARY_2);
        if (m != null) return m;
        boolean r;
        r = t.consume("&");
        r = r && item(t);
        t.exit(r);
        return r;
    }

    /**
     * '!' item
     */
    private static boolean primary_3(ParseTree t) {
        var m = t.enter(PRIMARY_3);
        if (m != null) return m;
        boolean r;
        r = t.consume("!");
        r = r && item(t);
        t.exit(r);
        return r;
    }

    /**
     * item '*'
     */
    private static boolean primary_4(ParseTree t) {
        var m = t.enter(PRIMARY_4);
        if (m != null) return m;
        boolean r;
        r = item(t);
        r = r && t.consume("*");
        t.exit(r);
        return r;
    }

    /**
     * item '+'
     */
    private static boolean primary_5(ParseTree t) {
        var m = t.enter(PRIMARY_5);
        if (m != null) return m;
        boolean r;
        r = item(t);
        r = r && t.consume("+");
        t.exit(r);
        return r;
    }

    /**
     * item:
     * *   | group
     * *   | optional
     * *   | NAME
     * *   | STRING
     */
    public static boolean item(ParseTree t) {
        var m = t.enter(ITEM);
        if (m != null) return m;
        boolean r;
        r = group(t);
        r = r || optional(t);
        r = r || t.consume(TokenType.NAME);
        r = r || t.consume(TokenType.STRING);
        t.exit(r);
        return r;
    }

    /**
     * group:
     * *   | '(' alt_list ')'
     */
    public static boolean group(ParseTree t) {
        var m = t.enter(GROUP);
        if (m != null) return m;
        boolean r;
        r = t.consume("(");
        r = r && alt_list(t);
        r = r && t.consume(")");
        t.exit(r);
        return r;
    }

    /**
     * optional:
     * *   | '[' alt_list ']'
     */
    public static boolean optional(ParseTree t) {
        var m = t.enter(OPTIONAL);
        if (m != null) return m;
        boolean r;
        r = t.consume("[");
        r = r && alt_list(t);
        r = r && t.consume("]");
        t.exit(r);
        return r;
    }

    /**
     * delimited:
     * *   | STRING '.' item '+'
     */
    public static boolean delimited(ParseTree t) {
        var m = t.enter(DELIMITED);
        if (m != null) return m;
        boolean r;
        r = t.consume(TokenType.STRING);
        r = r && t.consume(".");
        r = r && item(t);
        r = r && t.consume("+");
        t.exit(r);
        return r;
    }
}

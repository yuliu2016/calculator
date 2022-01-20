package org.fugalang.grammar.peg.parser;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.token.TokenType;

import static org.fugalang.grammar.peg.parser.MetaRules.*;

@SuppressWarnings("UnusedReturnValue")
public class MetaParser {

    /**
     * grammar:
     * *   | [NEWLINE] element+
     */
    public static boolean grammar(ParseTree t) {
        var m = t.enter(GRAMMAR);
        if (m != null) return m;
        boolean r;
        t.consume(TokenType.NEWLINE);
        r = element_loop(t);
        t.exit(r);
        return r;
    }

    private static boolean element_loop(ParseTree t) {
        t.enterLoop();
        var r = element(t);
        if (r) while (true) {
            if (!element(t)) break;
        }
        t.exitLoop();
        return r;
    }

    /**
     * element:
     * *   | directive
     * *   | rule
     */
    public static boolean element(ParseTree t) {
        var m = t.enter(ELEMENT);
        if (m != null) return m;
        boolean r;
        r = directive(t);
        r = r || rule(t);
        t.exit(r);
        return r;
    }

    /**
     * directive:
     * *   | '.' NAME '(' [','.argument+] ')' NEWLINE
     */
    public static boolean directive(ParseTree t) {
        var m = t.enter(DIRECTIVE);
        if (m != null) return m;
        boolean r;
        r = t.consume(".");
        r = r && t.consume(TokenType.NAME);
        r = r && t.consume("(");
        r = r && argument_loop(t);
        r = r && t.consume(")");
        r = r && t.consume(TokenType.NEWLINE);
        t.exit(r);
        return r;
    }

    private static boolean argument_loop(ParseTree t) {
        t.enterLoop();
        var r = argument(t);
        if (r) while (true) {
            var p = t.position();
            if (t.skip(",") && argument(t)) continue;
            t.reset(p);
            break;
        }
        t.exitLoop();
        return r;
    }

    /**
     * argument:
     * *   | STRING
     */
    public static boolean argument(ParseTree t) {
        var m = t.enter(ARGUMENT);
        if (m != null) return m;
        boolean r;
        r = t.consume(TokenType.STRING);
        t.exit(r);
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
     * *   | '[' NAME ['*'] ']'
     */
    public static boolean return_type(ParseTree t) {
        var m = t.enter(RETURN_TYPE);
        if (m != null) return m;
        boolean r;
        r = t.consume("[");
        r = r && t.consume(TokenType.NAME);
        if (r) t.consume("*");
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
     * *   | primary+ [inline_hint] [result_expr]
     */
    public static boolean sequence(ParseTree t) {
        var m = t.enter(SEQUENCE);
        if (m != null) return m;
        boolean r;
        r = primary_loop(t);
        if (r) inline_hint(t);
        if (r) result_expr(t);
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
     * inline_hint:
     * *   | '>>' [return_type]
     */
    public static boolean inline_hint(ParseTree t) {
        var m = t.enter(INLINE_HINT);
        if (m != null) return m;
        boolean r;
        r = t.consume(">>");
        if (r) return_type(t);
        t.exit(r);
        return r;
    }

    /**
     * result_expr:
     * *   | [NEWLINE] '{' expression '}'
     */
    public static boolean result_expr(ParseTree t) {
        var m = t.enter(RESULT_EXPR);
        if (m != null) return m;
        boolean r;
        t.consume(TokenType.NEWLINE);
        r = t.consume("{");
        r = r && expression(t);
        r = r && t.consume("}");
        t.exit(r);
        return r;
    }

    /**
     * expr_name:
     * *   | '.'.NAME+
     */
    public static boolean expr_name(ParseTree t) {
        var m = t.enter(EXPR_NAME);
        if (m != null) return m;
        boolean r;
        r = expr_name_loop(t);
        t.exit(r);
        return r;
    }

    private static boolean expr_name_loop(ParseTree t) {
        t.enterLoop();
        var r = t.consume(TokenType.NAME);
        if (r) while (true) {
            var p = t.position();
            if (t.skip(".") && t.consume(TokenType.NAME)) continue;
            t.reset(p);
            break;
        }
        t.exitLoop();
        return r;
    }

    /**
     * expr_arg:
     * *   | '%' NAME
     * *   | NUMBER
     * *   | expr_call
     * *   | expr_name
     */
    public static boolean expr_arg(ParseTree t) {
        var m = t.enter(EXPR_ARG);
        if (m != null) return m;
        boolean r;
        r = expr_arg_1(t);
        r = r || t.consume(TokenType.NUMBER);
        r = r || expr_call(t);
        r = r || expr_name(t);
        t.exit(r);
        return r;
    }

    /**
     * '%' NAME
     */
    private static boolean expr_arg_1(ParseTree t) {
        var m = t.enter(EXPR_ARG_1);
        if (m != null) return m;
        boolean r;
        r = t.consume("%");
        r = r && t.consume(TokenType.NAME);
        t.exit(r);
        return r;
    }

    /**
     * expr_call:
     * *   | expr_name '(' ','.expr_arg+ ')'
     */
    public static boolean expr_call(ParseTree t) {
        var m = t.enter(EXPR_CALL);
        if (m != null) return m;
        boolean r;
        r = expr_name(t);
        r = r && t.consume("(");
        r = r && expr_arg_loop(t);
        r = r && t.consume(")");
        t.exit(r);
        return r;
    }

    private static boolean expr_arg_loop(ParseTree t) {
        t.enterLoop();
        var r = expr_arg(t);
        if (r) while (true) {
            var p = t.position();
            if (t.skip(",") && expr_arg(t)) continue;
            t.reset(p);
            break;
        }
        t.exitLoop();
        return r;
    }

    /**
     * expression:
     * *   | expr_call
     * *   | NAME
     * *   | STRING
     */
    public static boolean expression(ParseTree t) {
        var m = t.enter(EXPRESSION);
        if (m != null) return m;
        boolean r;
        r = expr_call(t);
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
     * small_optional:
     * *   | (NAME | STRING) '?'
     */
    public static boolean small_optional(ParseTree t) {
        var m = t.enter(SMALL_OPTIONAL);
        if (m != null) return m;
        boolean r;
        r = small_optional_1(t);
        r = r && t.consume("?");
        t.exit(r);
        return r;
    }

    /**
     * NAME | STRING
     */
    private static boolean small_optional_1(ParseTree t) {
        var m = t.enter(SMALL_OPTIONAL_1);
        if (m != null) return m;
        boolean r;
        r = t.consume(TokenType.NAME);
        r = r || t.consume(TokenType.STRING);
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

    /**
     * custom_match:
     * *   | '@' expression
     */
    public static boolean custom_match(ParseTree t) {
        var m = t.enter(CUSTOM_MATCH);
        if (m != null) return m;
        boolean r;
        r = t.consume("@");
        r = r && expression(t);
        t.exit(r);
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
     * *   | custom_match
     * *   | small_optional
     * *   | NAME
     * *   | STRING
     */
    public static boolean item(ParseTree t) {
        var m = t.enter(ITEM);
        if (m != null) return m;
        boolean r;
        r = group(t);
        r = r || optional(t);
        r = r || custom_match(t);
        r = r || small_optional(t);
        r = r || t.consume(TokenType.NAME);
        r = r || t.consume(TokenType.STRING);
        t.exit(r);
        return r;
    }
}

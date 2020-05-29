package org.fugalang.grammar.peg.parser;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.token.TokenType;

import static org.fugalang.grammar.peg.parser.MetaRules.*;

@SuppressWarnings("UnusedReturnValue")
public class MetaParser {

    /**
     * rules: [NEWLINE] single_rule+
     */
    public static boolean rules(ParseTree t) {
        var m = t.enter(RULES);
        if (m != null) return m;
        boolean r;
        t.consume(TokenType.NEWLINE);
        r = single_rule_loop(t);
        t.exit(r);
        return r;
    }

    private static boolean single_rule_loop(ParseTree t) {
        t.enterLoop();
        var r = single_rule(t);
        if (r) while (true) {
            var p = t.position();
            if (!single_rule(t) || t.loopGuard(p)) break;
        }
        t.exitLoop();
        return r;
    }

    /**
     * single_rule: NAME ':' [NEWLINE '|'] or_rule NEWLINE
     */
    public static boolean single_rule(ParseTree t) {
        var m = t.enter(SINGLE_RULE);
        if (m != null) return m;
        boolean r;
        r = t.consume(TokenType.NAME);
        r = r && t.consume(":");
        if (r) single_rule_3(t);
        r = r && or_rule(t);
        r = r && t.consume(TokenType.NEWLINE);
        t.exit(r);
        return r;
    }

    /**
     * NEWLINE '|'
     */
    private static boolean single_rule_3(ParseTree t) {
        var m = t.enter(SINGLE_RULE_3);
        if (m != null) return m;
        boolean r;
        r = t.consume(TokenType.NEWLINE);
        r = r && t.consume("|");
        t.exit(r);
        return r;
    }

    /**
     * or_rule: and_rule ([NEWLINE] '|' and_rule)*
     */
    public static boolean or_rule(ParseTree t) {
        var m = t.enter(OR_RULE);
        if (m != null) return m;
        boolean r;
        r = and_rule(t);
        if (r) or_rule_2_loop(t);
        t.exit(r);
        return r;
    }

    private static void or_rule_2_loop(ParseTree t) {
        t.enterLoop();
        while (true) {
            var p = t.position();
            if (!or_rule_2(t) || t.loopGuard(p)) break;
        }
        t.exitLoop();
    }

    /**
     * [NEWLINE] '|' and_rule
     */
    private static boolean or_rule_2(ParseTree t) {
        var m = t.enter(OR_RULE_2);
        if (m != null) return m;
        boolean r;
        t.consume(TokenType.NEWLINE);
        r = t.consume("|");
        r = r && and_rule(t);
        t.exit(r);
        return r;
    }

    /**
     * and_rule: repeat+
     */
    public static boolean and_rule(ParseTree t) {
        var m = t.enter(AND_RULE);
        if (m != null) return m;
        boolean r;
        r = repeat_loop(t);
        t.exit(r);
        return r;
    }

    private static boolean repeat_loop(ParseTree t) {
        t.enterLoop();
        var r = repeat(t);
        if (r) while (true) {
            var p = t.position();
            if (!repeat(t) || t.loopGuard(p)) break;
        }
        t.exitLoop();
        return r;
    }

    /**
     * repeat: item '*' | item '+' | item
     */
    public static boolean repeat(ParseTree t) {
        var m = t.enter(REPEAT);
        if (m != null) return m;
        boolean r;
        r = repeat_1(t);
        r = r || repeat_2(t);
        r = r || item(t);
        t.exit(r);
        return r;
    }

    /**
     * item '*'
     */
    private static boolean repeat_1(ParseTree t) {
        var m = t.enter(REPEAT_1);
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
    private static boolean repeat_2(ParseTree t) {
        var m = t.enter(REPEAT_2);
        if (m != null) return m;
        boolean r;
        r = item(t);
        r = r && t.consume("+");
        t.exit(r);
        return r;
    }

    /**
     * item: group | optional | NAME | STRING
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
     * group: '(' or_rule ')'
     */
    public static boolean group(ParseTree t) {
        var m = t.enter(GROUP);
        if (m != null) return m;
        boolean r;
        r = t.consume("(");
        r = r && or_rule(t);
        r = r && t.consume(")");
        t.exit(r);
        return r;
    }

    /**
     * optional: '[' or_rule ']'
     */
    public static boolean optional(ParseTree t) {
        var m = t.enter(OPTIONAL);
        if (m != null) return m;
        boolean r;
        r = t.consume("[");
        r = r && or_rule(t);
        r = r && t.consume("]");
        t.exit(r);
        return r;
    }
}

package org.fugalang.core.grammar.pgen.parser;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.token.TokenType;

import static org.fugalang.core.grammar.pgen.parser.MetaRules.*;

@SuppressWarnings("UnusedReturnValue")
public class MetaParser {

    /**
     * rules: [NEWLINE] single_rule+
     */
    public static boolean rules(ParseTree t, int lv) {
        var m = t.enter(lv, RULES);
        if (m != null) return m;
        boolean r;
        t.consume(TokenType.NEWLINE);
        r = single_rule_loop(t, lv);
        t.exit(r);
        return r;
    }

    private static boolean single_rule_loop(ParseTree t, int lv) {
        t.enterLoop();
        var r = single_rule(t, lv + 1);
        if (r) while (true) {
            var p = t.position();
            if (!single_rule(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitLoop();
        return r;
    }

    /**
     * single_rule: NAME ':' [NEWLINE '|'] or_rule NEWLINE
     */
    public static boolean single_rule(ParseTree t, int lv) {
        var m = t.enter(lv, SINGLE_RULE);
        if (m != null) return m;
        boolean r;
        r = t.consume(TokenType.NAME);
        r = r && t.consume(":");
        if (r) single_rule_3(t, lv + 1);
        r = r && or_rule(t, lv + 1);
        r = r && t.consume(TokenType.NEWLINE);
        t.exit(r);
        return r;
    }

    /**
     * NEWLINE '|'
     */
    private static boolean single_rule_3(ParseTree t, int lv) {
        var m = t.enter(lv, SINGLE_RULE_3);
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
    public static boolean or_rule(ParseTree t, int lv) {
        var m = t.enter(lv, OR_RULE);
        if (m != null) return m;
        boolean r;
        r = and_rule(t, lv + 1);
        if (r) or_rule_2_loop(t, lv);
        t.exit(r);
        return r;
    }

    private static void or_rule_2_loop(ParseTree t, int lv) {
        t.enterLoop();
        while (true) {
            var p = t.position();
            if (!or_rule_2(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitLoop();
    }

    /**
     * [NEWLINE] '|' and_rule
     */
    private static boolean or_rule_2(ParseTree t, int lv) {
        var m = t.enter(lv, OR_RULE_2);
        if (m != null) return m;
        boolean r;
        t.consume(TokenType.NEWLINE);
        r = t.consume("|");
        r = r && and_rule(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * and_rule: repeat_rule+
     */
    public static boolean and_rule(ParseTree t, int lv) {
        var m = t.enter(lv, AND_RULE);
        if (m != null) return m;
        boolean r;
        r = repeat_rule_loop(t, lv);
        t.exit(r);
        return r;
    }

    private static boolean repeat_rule_loop(ParseTree t, int lv) {
        t.enterLoop();
        var r = repeat_rule(t, lv + 1);
        if (r) while (true) {
            var p = t.position();
            if (!repeat_rule(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitLoop();
        return r;
    }

    /**
     * repeat_rule: sub_rule ['*' | '+']
     */
    public static boolean repeat_rule(ParseTree t, int lv) {
        var m = t.enter(lv, REPEAT_RULE);
        if (m != null) return m;
        boolean r;
        r = sub_rule(t, lv + 1);
        if (r) repeat_rule_2(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * '*' | '+'
     */
    private static boolean repeat_rule_2(ParseTree t, int lv) {
        var m = t.enter(lv, REPEAT_RULE_2);
        if (m != null) return m;
        boolean r;
        r = t.consume("*");
        r = r || t.consume("+");
        t.exit(r);
        return r;
    }

    /**
     * sub_rule: group | optional | NAME | STRING
     */
    public static boolean sub_rule(ParseTree t, int lv) {
        var m = t.enter(lv, SUB_RULE);
        if (m != null) return m;
        boolean r;
        r = group(t, lv + 1);
        r = r || optional(t, lv + 1);
        r = r || t.consume(TokenType.NAME);
        r = r || t.consume(TokenType.STRING);
        t.exit(r);
        return r;
    }

    /**
     * group: '(' or_rule ')'
     */
    public static boolean group(ParseTree t, int lv) {
        var m = t.enter(lv, GROUP);
        if (m != null) return m;
        boolean r;
        r = t.consume("(");
        r = r && or_rule(t, lv + 1);
        r = r && t.consume(")");
        t.exit(r);
        return r;
    }

    /**
     * optional: '[' or_rule ']'
     */
    public static boolean optional(ParseTree t, int lv) {
        var m = t.enter(lv, OPTIONAL);
        if (m != null) return m;
        boolean r;
        r = t.consume("[");
        r = r && or_rule(t, lv + 1);
        r = r && t.consume("]");
        t.exit(r);
        return r;
    }
}

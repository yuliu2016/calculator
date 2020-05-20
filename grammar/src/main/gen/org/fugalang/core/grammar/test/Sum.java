package org.fugalang.core.grammar.test;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * sum: 'sum' '+' 'NUMBER' | 'NUMBER'
 */
public final class Sum extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("sum", RuleType.Disjunction);

    public static Sum of(ParseTreeNode node) {
        return new Sum(node);
    }

    private Sum(ParseTreeNode node) {
        super(RULE, node);
    }

    public Sum1 sumNumber() {
        return get(0, Sum1::of);
    }

    public boolean hasSumNumber() {
        return has(0, Sum1.RULE);
    }

    public String number() {
        return get(1, TokenType.NUMBER);
    }

    public boolean hasNumber() {
        return has(1, TokenType.NUMBER);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = Sum1.parse(t, lv + 1);
        r = r || t.consume(TokenType.NUMBER);
        t.exit(r);
        return r;
    }

    /**
     * 'sum' '+' 'NUMBER'
     */
    public static final class Sum1 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("sum:1", RuleType.Conjunction);

        public static Sum1 of(ParseTreeNode node) {
            return new Sum1(node);
        }

        private Sum1(ParseTreeNode node) {
            super(RULE, node);
        }

        public Sum sum() {
            return get(0, Sum::of);
        }

        public String number() {
            return get(2, TokenType.NUMBER);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (t.recursionGuard(lv)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = Sum.parse(t, lv + 1);
            r = r && t.consume("+");
            r = r && t.consume(TokenType.NUMBER);
            t.exit(r);
            return r;
        }
    }
}

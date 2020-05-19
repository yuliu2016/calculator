package org.fugalang.core.grammar.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * single_rule: 'NAME' ':' ['NEWLINE' '|'] 'or_rule' 'NEWLINE'
 */
public final class SingleRule extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("single_rule", RuleType.Conjunction);

    public static SingleRule of(ParseTreeNode node) {
        return new SingleRule(node);
    }

    private SingleRule(ParseTreeNode node) {
        super(RULE, node);
    }

    public String name() {
        return get(0, TokenType.NAME);
    }

    public SingleRule3 newline() {
        return get(2, SingleRule3::of);
    }

    public boolean hasNewline() {
        return has(2, SingleRule3.RULE);
    }

    public OrRule orRule() {
        return get(3, OrRule::of);
    }

    public String newline1() {
        return get(4, TokenType.NEWLINE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consume(TokenType.NAME);
        r = r && t.consume(":");
        if (r) SingleRule3.parse(t, lv + 1);
        r = r && OrRule.parse(t, lv + 1);
        r = r && t.consume(TokenType.NEWLINE);
        t.exit(r);
        return r;
    }

    /**
     * 'NEWLINE' '|'
     */
    public static final class SingleRule3 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("single_rule:3", RuleType.Conjunction);

        public static SingleRule3 of(ParseTreeNode node) {
            return new SingleRule3(node);
        }

        private SingleRule3(ParseTreeNode node) {
            super(RULE, node);
        }

        public String newline() {
            return get(0, TokenType.NEWLINE);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (t.recursionGuard(lv)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume(TokenType.NEWLINE);
            r = r && t.consume("|");
            t.exit(r);
            return r;
        }
    }
}

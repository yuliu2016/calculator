package org.fugalang.core.grammar.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * sub_rule: '(' 'or_rule' ')' | '[' 'or_rule' ']' | 'NAME' | 'STRING'
 */
public final class SubRule extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("sub_rule", RuleType.Disjunction);

    public static SubRule of(ParseTreeNode node) {
        return new SubRule(node);
    }

    private SubRule(ParseTreeNode node) {
        super(RULE, node);
    }

    public SubRule1 orRule() {
        return get(0, SubRule1::of);
    }

    public boolean hasOrRule() {
        return has(0, SubRule1.RULE);
    }

    public SubRule2 orRule1() {
        return get(1, SubRule2::of);
    }

    public boolean hasOrRule1() {
        return has(1, SubRule2.RULE);
    }

    public String name() {
        return get(2, TokenType.NAME);
    }

    public boolean hasName() {
        return has(2, TokenType.NAME);
    }

    public String string() {
        return get(3, TokenType.STRING);
    }

    public boolean hasString() {
        return has(3, TokenType.STRING);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = SubRule1.parse(t, lv + 1);
        r = r || SubRule2.parse(t, lv + 1);
        r = r || t.consume(TokenType.NAME);
        r = r || t.consume(TokenType.STRING);
        t.exit(r);
        return r;
    }

    /**
     * '(' 'or_rule' ')'
     */
    public static final class SubRule1 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("sub_rule:1", RuleType.Conjunction);

        public static SubRule1 of(ParseTreeNode node) {
            return new SubRule1(node);
        }

        private SubRule1(ParseTreeNode node) {
            super(RULE, node);
        }

        public OrRule orRule() {
            return get(1, OrRule::of);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume("(");
            r = r && OrRule.parse(t, lv + 1);
            r = r && t.consume(")");
            t.exit(r);
            return r;
        }
    }

    /**
     * '[' 'or_rule' ']'
     */
    public static final class SubRule2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("sub_rule:2", RuleType.Conjunction);

        public static SubRule2 of(ParseTreeNode node) {
            return new SubRule2(node);
        }

        private SubRule2(ParseTreeNode node) {
            super(RULE, node);
        }

        public OrRule orRule() {
            return get(1, OrRule::of);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume("[");
            r = r && OrRule.parse(t, lv + 1);
            r = r && t.consume("]");
            t.exit(r);
            return r;
        }
    }
}

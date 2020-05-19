package org.fugalang.core.grammar.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

import java.util.List;

/**
 * or_rule: 'and_rule' (['NEWLINE'] '|' 'and_rule')*
 */
public final class OrRule extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("or_rule", RuleType.Conjunction);

    public static OrRule of(ParseTreeNode node) {
        return new OrRule(node);
    }

    private OrRule(ParseTreeNode node) {
        super(RULE, node);
    }

    public AndRule andRule() {
        return get(0, AndRule::of);
    }

    public List<OrRule2> orRule2s() {
        return getList(1, OrRule2::of);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = AndRule.parse(t, lv + 1);
        if (r) parseOrRule2s(t, lv);
        t.exit(r);
        return r;
    }

    private static void parseOrRule2s(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!OrRule2.parse(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ['NEWLINE'] '|' 'and_rule'
     */
    public static final class OrRule2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("or_rule:2", RuleType.Conjunction);

        public static OrRule2 of(ParseTreeNode node) {
            return new OrRule2(node);
        }

        private OrRule2(ParseTreeNode node) {
            super(RULE, node);
        }

        public String newline() {
            return get(0, TokenType.NEWLINE);
        }

        public boolean hasNewline() {
            return has(0, TokenType.NEWLINE);
        }

        public AndRule andRule() {
            return get(2, AndRule::of);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (t.recursionGuard(lv)) return false;
            t.enter(lv, RULE);
            boolean r;
            t.consume(TokenType.NEWLINE);
            r = t.consume("|");
            r = r && AndRule.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}

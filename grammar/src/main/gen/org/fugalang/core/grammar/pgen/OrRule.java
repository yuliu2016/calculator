package org.fugalang.core.grammar.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * or_rule: 'and_rule' ('|' 'and_rule')*
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
        return AndRule.of(get(0));
    }

    public List<OrRule2> andRuleList() {
        return getList(1, OrRule2::of);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = AndRule.parse(t, lv + 1);
        if (r) parseAndRuleList(t, lv);
        t.exit(r);
        return r;
    }

    private static void parseAndRuleList(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!OrRule2.parse(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * '|' 'and_rule'
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

        public AndRule andRule() {
            return AndRule.of(get(1));
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume("|");
            r = r && AndRule.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}

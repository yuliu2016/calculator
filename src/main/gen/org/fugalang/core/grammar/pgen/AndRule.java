package org.fugalang.core.grammar.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * and_rule: 'repeat_rule' ('repeat_rule')*
 */
public final class AndRule extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("and_rule", RuleType.Conjunction);

    public static AndRule of(ParseTreeNode node) {
        return new AndRule(node);
    }

    private AndRule(ParseTreeNode node) {
        super(RULE, node);
    }

    public RepeatRule repeatRule() {
        return RepeatRule.of(get(0));
    }

    public List<AndRule2> andRule2List() {
        return getList(1, AndRule2::of);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = RepeatRule.parse(t, lv + 1);
        if (r) parseAndRule2List(t, lv);
        t.exit(r);
        return r;
    }

    private static void parseAndRule2List(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!AndRule2.parse(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * 'repeat_rule'
     */
    public static final class AndRule2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("and_rule:2", RuleType.Conjunction);

        public static AndRule2 of(ParseTreeNode node) {
            return new AndRule2(node);
        }

        private AndRule2(ParseTreeNode node) {
            super(RULE, node);
        }

        public RepeatRule repeatRule() {
            return RepeatRule.of(get(0));
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = RepeatRule.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}

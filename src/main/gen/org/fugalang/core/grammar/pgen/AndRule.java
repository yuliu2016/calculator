package org.fugalang.core.grammar.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * and_rule: 'repeat_rule' ('repeat_rule')*
 */
public final class AndRule extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("and_rule", RuleType.Conjunction, true);

    public static AndRule of(ParseTreeNode node) {
        return new AndRule(node);
    }

    private AndRule(ParseTreeNode node) {
        super(RULE, node);
    }

    public RepeatRule repeatRule() {
        return RepeatRule.of(getItem(0));
    }

    public List<AndRule2> andRule2List() {
        return getList(1, AndRule2::of);
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = RepeatRule.parse(t, l + 1);
        if (r) parseAndRule2List(t, l);
        t.exit(r);
        return r;
    }

    private static void parseAndRule2List(ParseTree t, int l) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!AndRule2.parse(t, l + 1)) break;
            if (t.guardLoopExit(p)) break;
        }
        t.exitCollection();
    }

    /**
     * 'repeat_rule'
     */
    public static final class AndRule2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("and_rule:2", RuleType.Conjunction, false);

        public static AndRule2 of(ParseTreeNode node) {
            return new AndRule2(node);
        }

        private AndRule2(ParseTreeNode node) {
            super(RULE, node);
        }

        public RepeatRule repeatRule() {
            return RepeatRule.of(getItem(0));
        }

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = RepeatRule.parse(t, l + 1);
            t.exit(r);
            return r;
        }
    }
}

package org.fugalang.core.grammar.pgen;

import org.fugalang.core.parser.*;

/**
 * repeat_rule: 'sub_rule' ['*' | '+']
 */
public final class RepeatRule extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("repeat_rule", RuleType.Conjunction);

    public static RepeatRule of(ParseTreeNode node) {
        return new RepeatRule(node);
    }

    private RepeatRule(ParseTreeNode node) {
        super(RULE, node);
    }

    public SubRule subRule() {
        return get(0, SubRule::of);
    }

    public RepeatRule2 timesOrPlus() {
        return get(1, RepeatRule2::of);
    }

    public boolean hasTimesOrPlus() {
        return has(1, RepeatRule2.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = SubRule.parse(t, lv + 1);
        if (r) RepeatRule2.parse(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * '*' | '+'
     */
    public static final class RepeatRule2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("repeat_rule:2", RuleType.Disjunction);

        public static RepeatRule2 of(ParseTreeNode node) {
            return new RepeatRule2(node);
        }

        private RepeatRule2(ParseTreeNode node) {
            super(RULE, node);
        }

        public boolean isTimes() {
            return is(0);
        }

        public boolean isPlus() {
            return is(1);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume("*");
            r = r || t.consume("+");
            t.exit(r);
            return r;
        }
    }
}

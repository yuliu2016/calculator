package org.fugalang.core.grammar.pgen;

import org.fugalang.core.parser.*;

/**
 * repeat_rule: 'sub_rule' ['*' | '+']
 */
public final class RepeatRule extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("repeat_rule", RuleType.Conjunction, true);

    public static RepeatRule of(ParseTreeNode node) {
        return new RepeatRule(node);
    }

    private RepeatRule(ParseTreeNode node) {
        super(RULE, node);
    }

    public SubRule subRule() {
        return SubRule.of(getItem(0));
    }

    public RepeatRule2 repeatRule2() {
        return RepeatRule2.of(getItem(1));
    }

    public boolean hasRepeatRule2() {
        return hasItemOfRule(1, RepeatRule2.RULE);
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = SubRule.parse(t, l + 1);
        if (r) RepeatRule2.parse(t, l + 1);
        t.exit(r);
        return r;
    }

    /**
     * '*' | '+'
     */
    public static final class RepeatRule2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("repeat_rule:2", RuleType.Disjunction, false);

        public static RepeatRule2 of(ParseTreeNode node) {
            return new RepeatRule2(node);
        }

        private RepeatRule2(ParseTreeNode node) {
            super(RULE, node);
        }

        public boolean isTokenTimes() {
            return getBoolean(0);
        }

        public boolean isTokenPlus() {
            return getBoolean(1);
        }

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = t.consumeToken("*");
            r = r || t.consumeToken("+");
            t.exit(r);
            return r;
        }
    }
}

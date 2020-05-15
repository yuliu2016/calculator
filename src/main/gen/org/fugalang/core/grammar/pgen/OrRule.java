package org.fugalang.core.grammar.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * or_rule: 'and_rule' ('|' 'and_rule')*
 */
public final class OrRule extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("or_rule", RuleType.Conjunction, true);

    public static OrRule of(ParseTreeNode node) {
        return new OrRule(node);
    }

    private OrRule(ParseTreeNode node) {
        super(RULE, node);
    }

    public AndRule andRule() {
        return AndRule.of(getItem(0));
    }

    public List<OrRule2> orRule2List() {
        return getList(1, OrRule2::of);
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = AndRule.parse(t, l + 1);
        if (r) parseOrRule2List(t, l);
        t.exit(r);
        return r;
    }

    private static void parseOrRule2List(ParseTree t, int l) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!OrRule2.parse(t, l + 1)) break;
            if (t.guardLoopExit(p)) break;
        }
        t.exitCollection();
    }

    /**
     * '|' 'and_rule'
     */
    public static final class OrRule2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("or_rule:2", RuleType.Conjunction, false);

        public static OrRule2 of(ParseTreeNode node) {
            return new OrRule2(node);
        }

        private OrRule2(ParseTreeNode node) {
            super(RULE, node);
        }

        public AndRule andRule() {
            return AndRule.of(getItem(1));
        }

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = t.consumeToken("|");
            r = r && AndRule.parse(t, l + 1);
            t.exit(r);
            return r;
        }
    }
}

package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * disjunction: 'conjunction' ('or' 'conjunction')*
 */
public final class Disjunction extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("disjunction", RuleType.Conjunction);

    public static Disjunction of(ParseTreeNode node) {
        return new Disjunction(node);
    }

    private Disjunction(ParseTreeNode node) {
        super(RULE, node);
    }

    public Conjunction conjunction() {
        return Conjunction.of(getItem(0));
    }

    public List<Disjunction2> disjunction2List() {
        return getList(1, Disjunction2::of);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = Conjunction.parse(t, lv + 1);
        if (r) parseDisjunction2List(t, lv);
        t.exit(r);
        return r;
    }

    private static void parseDisjunction2List(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!Disjunction2.parse(t, lv + 1)) break;
            if (t.guardLoopExit(p)) break;
        }
        t.exitCollection();
    }

    /**
     * 'or' 'conjunction'
     */
    public static final class Disjunction2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("disjunction:2", RuleType.Conjunction);

        public static Disjunction2 of(ParseTreeNode node) {
            return new Disjunction2(node);
        }

        private Disjunction2(ParseTreeNode node) {
            super(RULE, node);
        }

        public Conjunction conjunction() {
            return Conjunction.of(getItem(1));
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consumeToken("or");
            r = r && Conjunction.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}

package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * sum: 'term' ('sum_op' 'term')*
 */
public final class Sum extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("sum", RuleType.Conjunction);

    public static Sum of(ParseTreeNode node) {
        return new Sum(node);
    }

    private Sum(ParseTreeNode node) {
        super(RULE, node);
    }

    public Term term() {
        return Term.of(get(0));
    }

    public List<Sum2> sumOpTermList() {
        return getList(1, Sum2::of);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = Term.parse(t, lv + 1);
        if (r) parseSumOpTermList(t, lv);
        t.exit(r);
        return r;
    }

    private static void parseSumOpTermList(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!Sum2.parse(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * 'sum_op' 'term'
     */
    public static final class Sum2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("sum:2", RuleType.Conjunction);

        public static Sum2 of(ParseTreeNode node) {
            return new Sum2(node);
        }

        private Sum2(ParseTreeNode node) {
            super(RULE, node);
        }

        public SumOp sumOp() {
            return SumOp.of(get(0));
        }

        public Term term() {
            return Term.of(get(1));
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = SumOp.parse(t, lv + 1);
            r = r && Term.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}

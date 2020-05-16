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
        return get(0, Term::of);
    }

    public List<Sum2> sumOpTerms() {
        return getList(1, Sum2::of);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = Term.parse(t, lv + 1);
        if (r) parseSumOpTerms(t, lv);
        t.exit(r);
        return r;
    }

    private static void parseSumOpTerms(ParseTree t, int lv) {
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
            return get(0, SumOp::of);
        }

        public Term term() {
            return get(1, Term::of);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (t.recursionGuard(lv)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = SumOp.parse(t, lv + 1);
            r = r && Term.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}

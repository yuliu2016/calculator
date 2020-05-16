package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * term: 'pipeline' ('term_op' 'pipeline')*
 */
public final class Term extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("term", RuleType.Conjunction);

    public static Term of(ParseTreeNode node) {
        return new Term(node);
    }

    private Term(ParseTreeNode node) {
        super(RULE, node);
    }

    public Pipeline pipeline() {
        return get(0, Pipeline::of);
    }

    public List<Term2> termOpPipelineList() {
        return getList(1, Term2::of);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = Pipeline.parse(t, lv + 1);
        if (r) parseTermOpPipelines(t, lv);
        t.exit(r);
        return r;
    }

    private static void parseTermOpPipelines(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!Term2.parse(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * 'term_op' 'pipeline'
     */
    public static final class Term2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("term:2", RuleType.Conjunction);

        public static Term2 of(ParseTreeNode node) {
            return new Term2(node);
        }

        private Term2(ParseTreeNode node) {
            super(RULE, node);
        }

        public TermOp termOp() {
            return get(0, TermOp::of);
        }

        public Pipeline pipeline() {
            return get(1, Pipeline::of);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = TermOp.parse(t, lv + 1);
            r = r && Pipeline.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}

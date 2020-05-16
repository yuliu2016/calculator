package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * pipeline: 'factor' ('->' 'pipe_expr')*
 */
public final class Pipeline extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("pipeline", RuleType.Conjunction);

    public static Pipeline of(ParseTreeNode node) {
        return new Pipeline(node);
    }

    private Pipeline(ParseTreeNode node) {
        super(RULE, node);
    }

    public Factor factor() {
        return Factor.of(get(0));
    }

    public List<Pipeline2> pipeExprList() {
        return getList(1, Pipeline2::of);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = Factor.parse(t, lv + 1);
        if (r) parsePipeExprList(t, lv);
        t.exit(r);
        return r;
    }

    private static void parsePipeExprList(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!Pipeline2.parse(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * '->' 'pipe_expr'
     */
    public static final class Pipeline2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("pipeline:2", RuleType.Conjunction);

        public static Pipeline2 of(ParseTreeNode node) {
            return new Pipeline2(node);
        }

        private Pipeline2(ParseTreeNode node) {
            super(RULE, node);
        }

        public PipeExpr pipeExpr() {
            return PipeExpr.of(get(1));
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume("->");
            r = r && PipeExpr.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}

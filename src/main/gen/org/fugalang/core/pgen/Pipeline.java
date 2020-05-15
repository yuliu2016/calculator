package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * pipeline: 'factor' ('->' 'pipe_expr')*
 */
public final class Pipeline extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("pipeline", RuleType.Conjunction, true);

    public static Pipeline of(ParseTreeNode node) {
        return new Pipeline(node);
    }

    private Pipeline(ParseTreeNode node) {
        super(RULE, node);
    }

    public Factor factor() {
        return Factor.of(getItem(0));
    }

    public List<Pipeline2> pipeline2List() {
        return getList(1, Pipeline2::of);
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = Factor.parse(t, l + 1);
        if (r) parsePipeline2List(t, l);
        t.exit(r);
        return r;
    }

    private static void parsePipeline2List(ParseTree t, int l) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!Pipeline2.parse(t, l + 1)) break;
            if (t.guardLoopExit(p)) break;
        }
        t.exitCollection();
    }

    /**
     * '->' 'pipe_expr'
     */
    public static final class Pipeline2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("pipeline:2", RuleType.Conjunction, false);

        public static Pipeline2 of(ParseTreeNode node) {
            return new Pipeline2(node);
        }

        private Pipeline2(ParseTreeNode node) {
            super(RULE, node);
        }

        public PipeExpr pipeExpr() {
            return PipeExpr.of(getItem(1));
        }

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = t.consumeToken("->");
            r = r && PipeExpr.parse(t, l + 1);
            t.exit(r);
            return r;
        }
    }
}

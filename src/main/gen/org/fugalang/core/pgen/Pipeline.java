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

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = Factor.parse(parseTree, level + 1);
        if (result) parsePipeline2List(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    private static void parsePipeline2List(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return;
        }
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!Pipeline2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();
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

        public boolean isTokenPipe() {
            return true;
        }

        public PipeExpr pipeExpr() {
            return PipeExpr.of(getItem(1));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("->");
            result = result && PipeExpr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}

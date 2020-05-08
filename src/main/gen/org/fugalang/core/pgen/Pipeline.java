package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
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

    private List<Pipeline2> pipeline2List;

    @Override
    protected void buildRule() {
        addRequired(factor());
        addRequired(pipeline2List());
    }

    public Factor factor() {
        var element = getItem(0);
        element.failIfAbsent(Factor.RULE);
        return Factor.of(element);
    }

    public List<Pipeline2> pipeline2List() {
        if (pipeline2List != null) {
            return pipeline2List;
        }
        List<Pipeline2> result = null;
        var element = getItem(1);
        for (var node : element.asCollection()) {
            if (result == null) {
                result = new ArrayList<>();
            }
            result.add(Pipeline2.of(node));
        }
        pipeline2List = result == null ? Collections.emptyList() : result;
        return pipeline2List;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
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

        @Override
        protected void buildRule() {
            addRequired(isTokenPipe(), "->");
            addRequired(pipeExpr());
        }

        public boolean isTokenPipe() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public PipeExpr pipeExpr() {
            var element = getItem(1);
            element.failIfAbsent(PipeExpr.RULE);
            return PipeExpr.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("->");
            result = result && PipeExpr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}

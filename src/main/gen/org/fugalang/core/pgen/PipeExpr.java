package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * pipe_expr: 'factor' ('->' 'factor')*
 */
public final class PipeExpr extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("pipe_expr", RuleType.Conjunction, true);

    public static PipeExpr of(ParseTreeNode node) {
        return new PipeExpr(node);
    }

    private PipeExpr(ParseTreeNode node) {
        super(RULE, node);
    }

    private List<PipeExpr2> pipeExpr2List;

    @Override
    protected void buildRule() {
        addRequired(factor());
        addRequired(pipeExpr2List());
    }

    public Factor factor() {
        var element = getItem(0);
        element.failIfAbsent(Factor.RULE);
        return Factor.of(element);
    }

    public List<PipeExpr2> pipeExpr2List() {
        if (pipeExpr2List != null) {
            return pipeExpr2List;
        }
        List<PipeExpr2> result = null;
        var element = getItem(1);
        for (var node : element.asCollection()) {
            if (result == null) {
                result = new ArrayList<>();
            }
            result.add(PipeExpr2.of(node));
        }
        pipeExpr2List = result == null ? Collections.emptyList() : result;
        return pipeExpr2List;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = Factor.parse(parseTree, level + 1);
        parseTree.enterCollection();
        if (result) while (true) {
            var pos = parseTree.position();
            if (!PipeExpr2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * '->' 'factor'
     */
    public static final class PipeExpr2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("pipe_expr:2", RuleType.Conjunction, false);

        public static PipeExpr2 of(ParseTreeNode node) {
            return new PipeExpr2(node);
        }

        private PipeExpr2(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired(isTokenPipe(), "->");
            addRequired(factor());
        }

        public boolean isTokenPipe() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public Factor factor() {
            var element = getItem(1);
            element.failIfAbsent(Factor.RULE);
            return Factor.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("->");
            result = result && Factor.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}

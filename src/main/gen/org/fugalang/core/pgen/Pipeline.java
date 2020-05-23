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
        return get(0, Factor::of);
    }

    public List<Pipeline2> pipeExprs() {
        return getList(1, Pipeline2::of);
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
            return get(1, PipeExpr::of);
        }
    }
}

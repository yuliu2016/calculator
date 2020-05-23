package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * pipeline: factor ('->' pipe_expr)*
 */
public final class Pipeline extends NodeWrapper {

    public Pipeline(ParseTreeNode node) {
        super(node);
    }

    public Factor factor() {
        return get(0, Factor.class);
    }

    public List<Pipeline2> pipeExprs() {
        return getList(1, Pipeline2.class);
    }

    /**
     * '->' pipe_expr
     */
    public static final class Pipeline2 extends NodeWrapper {

        public Pipeline2(ParseTreeNode node) {
            super(node);
        }

        public PipeExpr pipeExpr() {
            return get(1, PipeExpr.class);
        }
    }
}

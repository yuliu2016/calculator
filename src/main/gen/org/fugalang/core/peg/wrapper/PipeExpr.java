package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * pipe_expr:
 * *   | pipe_expr '->' factor
 * *   | factor
 */
public final class PipeExpr extends NodeWrapper {

    public PipeExpr(ParseTreeNode node) {
        super(node);
    }

    public PipeExpr1 pipeExprPipeFactor() {
        return new PipeExpr1(get(0));
    }

    public boolean hasPipeExprPipeFactor() {
        return has(0);
    }

    public Factor factor() {
        return new Factor(get(1));
    }

    public boolean hasFactor() {
        return has(1);
    }

    /**
     * pipe_expr '->' factor
     */
    public static final class PipeExpr1 extends NodeWrapper {

        public PipeExpr1(ParseTreeNode node) {
            super(node);
        }

        public PipeExpr pipeExpr() {
            return new PipeExpr(get(0));
        }

        public Factor factor() {
            return new Factor(get(2));
        }
    }
}

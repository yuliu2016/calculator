package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * pipe: pipe '->' pipe_expr | factor
 */
public final class Pipe extends NodeWrapper {

    public Pipe(ParseTreeNode node) {
        super(node);
    }

    public Pipe1 pipePipePipeExpr() {
        return get(0, Pipe1.class);
    }

    public boolean hasPipePipePipeExpr() {
        return has(0);
    }

    public Factor factor() {
        return get(1, Factor.class);
    }

    public boolean hasFactor() {
        return has(1);
    }

    /**
     * pipe '->' pipe_expr
     */
    public static final class Pipe1 extends NodeWrapper {

        public Pipe1(ParseTreeNode node) {
            super(node);
        }

        public Pipe pipe() {
            return get(0, Pipe.class);
        }

        public PipeExpr pipeExpr() {
            return get(2, PipeExpr.class);
        }
    }
}

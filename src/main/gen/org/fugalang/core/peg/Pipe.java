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
        return new Pipe1(get(0));
    }

    public boolean hasPipePipePipeExpr() {
        return has(0);
    }

    public Factor factor() {
        return new Factor(get(1));
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
            return new Pipe(get(0));
        }

        public PipeExpr pipeExpr() {
            return new PipeExpr(get(2));
        }
    }
}

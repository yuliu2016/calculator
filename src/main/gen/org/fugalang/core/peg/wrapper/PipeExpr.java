package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * pipe_expr: pipe_for | factor
 */
public final class PipeExpr extends NodeWrapper {

    public PipeExpr(ParseTreeNode node) {
        super(node);
    }

    public PipeFor pipeFor() {
        return new PipeFor(get(0));
    }

    public boolean hasPipeFor() {
        return has(0);
    }

    public Factor factor() {
        return new Factor(get(1));
    }

    public boolean hasFactor() {
        return has(1);
    }
}

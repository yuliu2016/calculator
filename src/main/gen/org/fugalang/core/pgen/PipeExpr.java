package org.fugalang.core.pgen;

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
        return get(0, PipeFor.class);
    }

    public boolean hasPipeFor() {
        return has(0);
    }

    public Factor factor() {
        return get(1, Factor.class);
    }

    public boolean hasFactor() {
        return has(1);
    }
}

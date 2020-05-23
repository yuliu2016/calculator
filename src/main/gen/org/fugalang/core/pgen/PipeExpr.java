package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

/**
 * pipe_expr: 'pipe_for' | 'factor'
 */
public final class PipeExpr extends NodeWrapper {

    public PipeExpr(ParseTreeNode node) {
        super(ParserRules.PIPE_EXPR, node);
    }

    public PipeFor pipeFor() {
        return get(0, PipeFor::new);
    }

    public boolean hasPipeFor() {
        return has(0);
    }

    public Factor factor() {
        return get(1, Factor::new);
    }

    public boolean hasFactor() {
        return has(1);
    }
}

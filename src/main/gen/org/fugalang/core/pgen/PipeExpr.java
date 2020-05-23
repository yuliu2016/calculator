package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * pipe_expr: 'pipe_for' | 'factor'
 */
public final class PipeExpr extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("pipe_expr", RuleType.Disjunction);

    public static PipeExpr of(ParseTreeNode node) {
        return new PipeExpr(node);
    }

    private PipeExpr(ParseTreeNode node) {
        super(RULE, node);
    }

    public PipeFor pipeFor() {
        return get(0, PipeFor::of);
    }

    public boolean hasPipeFor() {
        return has(0, PipeFor.RULE);
    }

    public Factor factor() {
        return get(1, Factor::of);
    }

    public boolean hasFactor() {
        return has(1, Factor.RULE);
    }
}

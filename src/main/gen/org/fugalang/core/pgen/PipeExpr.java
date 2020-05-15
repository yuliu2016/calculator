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
        return PipeFor.of(get(0));
    }

    public boolean hasPipeFor() {
        return has(0, PipeFor.RULE);
    }

    public Factor factor() {
        return Factor.of(get(1));
    }

    public boolean hasFactor() {
        return has(1, Factor.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = PipeFor.parse(t, lv + 1);
        r = r || Factor.parse(t, lv + 1);
        t.exit(r);
        return r;
    }
}

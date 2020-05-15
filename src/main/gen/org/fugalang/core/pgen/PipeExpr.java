package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * pipe_expr: 'pipe_for' | 'factor'
 */
public final class PipeExpr extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("pipe_expr", RuleType.Disjunction, true);

    public static PipeExpr of(ParseTreeNode node) {
        return new PipeExpr(node);
    }

    private PipeExpr(ParseTreeNode node) {
        super(RULE, node);
    }

    public PipeFor pipeFor() {
        return PipeFor.of(getItem(0));
    }

    public boolean hasPipeFor() {
        return hasItemOfRule(0, PipeFor.RULE);
    }

    public Factor factor() {
        return Factor.of(getItem(1));
    }

    public boolean hasFactor() {
        return hasItemOfRule(1, Factor.RULE);
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = PipeFor.parse(t, l + 1);
        r = r || Factor.parse(t, l + 1);
        t.exit(r);
        return r;
    }
}

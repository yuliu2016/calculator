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

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = PipeFor.parse(parseTree, level + 1);
        result = result || Factor.parse(parseTree, level + 1);

        parseTree.exit(result);
        return result;
    }
}

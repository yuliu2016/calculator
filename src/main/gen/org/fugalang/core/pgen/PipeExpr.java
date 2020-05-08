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

    @Override
    protected void buildRule() {
        addChoice(pipeForOrNull());
        addChoice(factorOrNull());
    }

    public PipeFor pipeFor() {
        var element = getItem(0);
        element.failIfAbsent(PipeFor.RULE);
        return PipeFor.of(element);
    }

    public PipeFor pipeForOrNull() {
        var element = getItem(0);
        if (!element.isPresent(PipeFor.RULE)) {
            return null;
        }
        return PipeFor.of(element);
    }

    public boolean hasPipeFor() {
        var element = getItem(0);
        return element.isPresent(PipeFor.RULE);
    }

    public Factor factor() {
        var element = getItem(1);
        element.failIfAbsent(Factor.RULE);
        return Factor.of(element);
    }

    public Factor factorOrNull() {
        var element = getItem(1);
        if (!element.isPresent(Factor.RULE)) {
            return null;
        }
        return Factor.of(element);
    }

    public boolean hasFactor() {
        var element = getItem(1);
        return element.isPresent(Factor.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = PipeFor.parse(parseTree, level + 1);
        result = result || Factor.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}

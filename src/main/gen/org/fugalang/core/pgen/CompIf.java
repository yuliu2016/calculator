package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * comp_if: 'if' 'expr' ['comp_iter']
 */
public final class CompIf extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("comp_if", RuleType.Conjunction, true);

    public static CompIf of(ParseTreeNode node) {
        return new CompIf(node);
    }

    private CompIf(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addRequired(isTokenIf(), "if");
        addRequired(expr());
        addOptional(compIter());
    }

    public boolean isTokenIf() {
        var element = getItem(0);
        element.failIfAbsent();
        return element.asBoolean();
    }

    public Expr expr() {
        var element = getItem(1);
        element.failIfAbsent(Expr.RULE);
        return Expr.of(element);
    }

    public CompIter compIter() {
        var element = getItem(2);
        element.failIfAbsent(CompIter.RULE);
        return CompIter.of(element);
    }

    public CompIter compIterOrNull() {
        var element = getItem(2);
        if (!element.isPresent(CompIter.RULE)) {
            return null;
        }
        return CompIter.of(element);
    }

    public boolean hasCompIter() {
        var element = getItem(2);
        return element.isPresent(CompIter.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("if");
        result = result && Expr.parse(parseTree, level + 1);
        if (result) CompIter.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}

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
        addRequired("isTokenIf", isTokenIf());
        addRequired("expr", expr());
        addOptional("compIter", compIter());
    }

    public boolean isTokenIf() {
        var element = getItem(0);
        return element.asBoolean();
    }

    public Expr expr() {
        var element = getItem(1);
        if (!element.isPresent()) return null;
        return Expr.of(element);
    }

    public CompIter compIter() {
        var element = getItem(2);
        if (!element.isPresent()) return null;
        return CompIter.of(element);
    }

    public boolean hasCompIter() {
        return compIter() != null;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeTokenLiteral("if");
        result = result && Expr.parse(parseTree, level + 1);
        CompIter.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}
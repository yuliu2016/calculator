package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * named_expr_star: 'star_expr' | 'named_expr'
 */
public final class NamedExprStar extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("named_expr_star", RuleType.Disjunction, true);

    public static NamedExprStar of(ParseTreeNode node) {
        return new NamedExprStar(node);
    }

    private NamedExprStar(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addChoice(starExprOrNull());
        addChoice(namedExprOrNull());
    }

    public StarExpr starExpr() {
        var element = getItem(0);
        element.failIfAbsent(StarExpr.RULE);
        return StarExpr.of(element);
    }

    public StarExpr starExprOrNull() {
        var element = getItem(0);
        if (!element.isPresent(StarExpr.RULE)) {
            return null;
        }
        return StarExpr.of(element);
    }

    public boolean hasStarExpr() {
        var element = getItem(0);
        return element.isPresent(StarExpr.RULE);
    }

    public NamedExpr namedExpr() {
        var element = getItem(1);
        element.failIfAbsent(NamedExpr.RULE);
        return NamedExpr.of(element);
    }

    public NamedExpr namedExprOrNull() {
        var element = getItem(1);
        if (!element.isPresent(NamedExpr.RULE)) {
            return null;
        }
        return NamedExpr.of(element);
    }

    public boolean hasNamedExpr() {
        var element = getItem(1);
        return element.isPresent(NamedExpr.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = StarExpr.parse(parseTree, level + 1);
        result = result || NamedExpr.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}

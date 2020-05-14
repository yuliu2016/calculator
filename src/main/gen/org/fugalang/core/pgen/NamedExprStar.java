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

    public StarExpr starExpr() {
        return StarExpr.of(getItem(0));
    }

    public boolean hasStarExpr() {
        return hasItemOfRule(0, StarExpr.RULE);
    }

    public NamedExpr namedExpr() {
        return NamedExpr.of(getItem(1));
    }

    public boolean hasNamedExpr() {
        return hasItemOfRule(1, NamedExpr.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = StarExpr.parse(parseTree, level + 1);
        result = result || NamedExpr.parse(parseTree, level + 1);

        parseTree.exit(result);
        return result;
    }
}

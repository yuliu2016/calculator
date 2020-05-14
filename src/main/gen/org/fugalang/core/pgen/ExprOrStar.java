package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * expr_or_star: 'star_expr' | 'expr'
 */
public final class ExprOrStar extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("expr_or_star", RuleType.Disjunction, true);

    public static ExprOrStar of(ParseTreeNode node) {
        return new ExprOrStar(node);
    }

    private ExprOrStar(ParseTreeNode node) {
        super(RULE, node);
    }

    public StarExpr starExpr() {
        return StarExpr.of(getItem(0));
    }

    public boolean hasStarExpr() {
        return hasItemOfRule(0, StarExpr.RULE);
    }

    public Expr expr() {
        return Expr.of(getItem(1));
    }

    public boolean hasExpr() {
        return hasItemOfRule(1, Expr.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = StarExpr.parse(parseTree, level + 1);
        result = result || Expr.parse(parseTree, level + 1);

        parseTree.exit(result);
        return result;
    }
}

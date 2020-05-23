package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * expr_or_star: 'star_expr' | 'expr'
 */
public final class ExprOrStar extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("expr_or_star", RuleType.Disjunction);

    public static ExprOrStar of(ParseTreeNode node) {
        return new ExprOrStar(node);
    }

    private ExprOrStar(ParseTreeNode node) {
        super(RULE, node);
    }

    public StarExpr starExpr() {
        return get(0, StarExpr::of);
    }

    public boolean hasStarExpr() {
        return has(0, StarExpr.RULE);
    }

    public Expr expr() {
        return get(1, Expr::of);
    }

    public boolean hasExpr() {
        return has(1, Expr.RULE);
    }
}

package org.fugalang.core.pgen;

import org.fugalang.core.parser.DisjunctionRule;

// expr_or_star: 'expr' | 'star_expr'
public final class ExprOrStar extends DisjunctionRule {
    public static final String RULE_NAME = "expr_or_star";

    private final Expr expr;
    private final StarExpr starExpr;

    public ExprOrStar(
            Expr expr,
            StarExpr starExpr
    ) {
        this.expr = expr;
        this.starExpr = starExpr;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addChoice("expr", expr);
        addChoice("starExpr", starExpr);
    }

    public Expr expr() {
        return expr;
    }

    public StarExpr starExpr() {
        return starExpr;
    }
}

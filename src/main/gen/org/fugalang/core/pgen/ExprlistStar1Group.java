package org.fugalang.core.pgen;

// 'expr' | 'star_expr'
public class ExprlistStar1Group {
    public final Expr expr;
    public final StarExpr starExpr;

    public ExprlistStar1Group(
            Expr expr,
            StarExpr starExpr
    ) {
        this.expr = expr;
        this.starExpr = starExpr;
    }
}

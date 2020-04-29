package org.fugalang.core.pgen;

// 'expr' | 'star_expr'
public class ExprlistStar2Group2Group {
    public final Expr expr;
    public final StarExpr starExpr;

    public ExprlistStar2Group2Group(
            Expr expr,
            StarExpr starExpr
    ) {
        this.expr = expr;
        this.starExpr = starExpr;
    }
}

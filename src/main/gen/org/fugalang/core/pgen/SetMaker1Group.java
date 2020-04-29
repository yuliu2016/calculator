package org.fugalang.core.pgen;

// 'expr' | 'star_expr'
public class SetMaker1Group {
    public final Expr expr;
    public final StarExpr starExpr;

    public SetMaker1Group(
            Expr expr,
            StarExpr starExpr
    ) {
        this.expr = expr;
        this.starExpr = starExpr;
    }
}

package org.fugalang.core.pgen;

// ':=' 'expr'
public class NamedexprExpr2Group {
    public final boolean isTokenAsgnExpr;
    public final Expr expr;

    public NamedexprExpr2Group(
            boolean isTokenAsgnExpr,
            Expr expr
    ) {
        this.isTokenAsgnExpr = isTokenAsgnExpr;
        this.expr = expr;
    }
}

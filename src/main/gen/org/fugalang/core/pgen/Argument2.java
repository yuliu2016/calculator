package org.fugalang.core.pgen;

// 'NAME' ':=' 'expr'
public class Argument2 {
    public final Object name;
    public final boolean isTokenAsgnExpr;
    public final Expr expr;

    public Argument2(
            Object name,
            boolean isTokenAsgnExpr,
            Expr expr
    ) {
        this.name = name;
        this.isTokenAsgnExpr = isTokenAsgnExpr;
        this.expr = expr;
    }
}

package org.fugalang.core.pgen;

// 'if' 'disjunction' '?' 'disjunction' 'else' 'expr'
public class Expr1 {
    public final boolean isTokenIf;
    public final Disjunction disjunction;
    public final boolean isTokenTernery;
    public final Disjunction disjunction1;
    public final boolean isTokenElse;
    public final Expr expr;

    public Expr1(
            boolean isTokenIf,
            Disjunction disjunction,
            boolean isTokenTernery,
            Disjunction disjunction1,
            boolean isTokenElse,
            Expr expr
    ) {
        this.isTokenIf = isTokenIf;
        this.disjunction = disjunction;
        this.isTokenTernery = isTokenTernery;
        this.disjunction1 = disjunction1;
        this.isTokenElse = isTokenElse;
        this.expr = expr;
    }
}

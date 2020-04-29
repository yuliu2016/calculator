package org.fugalang.core.pgen;

// ',' 'expr'
public class Exprlist2Group {
    public final boolean isTokenComma;
    public final Expr expr;

    public Exprlist2Group(
            boolean isTokenComma,
            Expr expr
    ) {
        this.isTokenComma = isTokenComma;
        this.expr = expr;
    }
}

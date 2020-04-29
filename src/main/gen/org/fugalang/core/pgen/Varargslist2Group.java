package org.fugalang.core.pgen;

// '=' 'expr'
public class Varargslist2Group {
    public final boolean isTokenAssign;
    public final Expr expr;

    public Varargslist2Group(
            boolean isTokenAssign,
            Expr expr
    ) {
        this.isTokenAssign = isTokenAssign;
        this.expr = expr;
    }
}

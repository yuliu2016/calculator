package org.fugalang.core.pgen;

// ['expr'] ':' ['expr'] ['sliceop']
public class Subscript2 {
    public final Expr expr;
    public final boolean isTokenColon;
    public final Expr expr1;
    public final Sliceop sliceop;

    public Subscript2(
            Expr expr,
            boolean isTokenColon,
            Expr expr1,
            Sliceop sliceop
    ) {
        this.expr = expr;
        this.isTokenColon = isTokenColon;
        this.expr1 = expr1;
        this.sliceop = sliceop;
    }
}

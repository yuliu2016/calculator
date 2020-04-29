package org.fugalang.core.pgen;

// sliceop: ':' ['expr']
public class Sliceop {
    public final boolean isTokenColon;
    public final Expr expr;

    public Sliceop(
            boolean isTokenColon,
            Expr expr
    ) {
        this.isTokenColon = isTokenColon;
        this.expr = expr;
    }
}

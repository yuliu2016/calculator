package org.fugalang.core.pgen;

// ':' 'expr'
public class Funcdef4Group1 {
    public final boolean isTokenColon;
    public final Expr expr;

    public Funcdef4Group1(
            boolean isTokenColon,
            Expr expr
    ) {
        this.isTokenColon = isTokenColon;
        this.expr = expr;
    }
}

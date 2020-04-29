package org.fugalang.core.pgen;

// subscript: 'expr' | ['expr'] ':' ['expr'] ['sliceop']
public class Subscript {
    public final Expr expr;
    public final Subscript2 subscript2;

    public Subscript(
            Expr expr,
            Subscript2 subscript2
    ) {
        this.expr = expr;
        this.subscript2 = subscript2;
    }
}

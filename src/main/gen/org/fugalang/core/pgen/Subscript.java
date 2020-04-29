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

    // ['expr'] ':' ['expr'] ['sliceop']
    public static class Subscript2 {
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
}

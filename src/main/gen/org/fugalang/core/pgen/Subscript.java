package org.fugalang.core.pgen;

// subscript: 'expr' | ['expr'] ':' ['expr'] ['sliceop']
public class Subscript {
    private final Expr expr;
    private final Subscript2 subscript2;

    public Subscript(
            Expr expr,
            Subscript2 subscript2
    ) {
        this.expr = expr;
        this.subscript2 = subscript2;
    }

    public Expr getExpr() {
        return expr;
    }

    public Subscript2 getSubscript2() {
        return subscript2;
    }

    // ['expr'] ':' ['expr'] ['sliceop']
    public static class Subscript2 {
        private final Expr expr;
        private final boolean isTokenColon;
        private final Expr expr1;
        private final Sliceop sliceop;

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

        public Expr getExpr() {
            return expr;
        }

        public boolean getIsTokenColon() {
            return isTokenColon;
        }

        public Expr getExpr1() {
            return expr1;
        }

        public Sliceop getSliceop() {
            return sliceop;
        }
    }
}

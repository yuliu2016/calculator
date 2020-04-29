package org.fugalang.core.pgen;

import java.util.Optional;

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

        public Optional<Expr> getExpr() {
            return Optional.ofNullable(expr);
        }

        public boolean getIsTokenColon() {
            return isTokenColon;
        }

        public Optional<Expr> getExpr1() {
            return Optional.ofNullable(expr1);
        }

        public Optional<Sliceop> getSliceop() {
            return Optional.ofNullable(sliceop);
        }
    }
}

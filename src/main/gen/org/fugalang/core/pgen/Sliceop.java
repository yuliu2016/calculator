package org.fugalang.core.pgen;

import java.util.Optional;

// sliceop: ':' ['expr']
public class Sliceop {
    private final boolean isTokenColon;
    private final Expr expr;

    public Sliceop(
            boolean isTokenColon,
            Expr expr
    ) {
        this.isTokenColon = isTokenColon;
        this.expr = expr;
    }

    public boolean getIsTokenColon() {
        return isTokenColon;
    }

    public Optional<Expr> getExpr() {
        return Optional.ofNullable(expr);
    }
}

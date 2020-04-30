package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.Optional;

// sliceop: ':' ['expr']
public final class Sliceop extends ConjunctionRule {
    private final boolean isTokenColon;
    private final Expr expr;

    public Sliceop(
            boolean isTokenColon,
            Expr expr
    ) {
        this.isTokenColon = isTokenColon;
        this.expr = expr;

        addRequired("isTokenColon", isTokenColon);
        addOptional("expr", expr);
    }

    public boolean getIsTokenColon() {
        return isTokenColon;
    }

    public Optional<Expr> getExpr() {
        return Optional.ofNullable(expr);
    }
}

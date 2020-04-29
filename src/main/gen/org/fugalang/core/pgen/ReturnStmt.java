package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.Optional;

// return_stmt: 'return' ['exprlist_star']
public final class ReturnStmt extends ConjunctionRule {
    private final boolean isTokenReturn;
    private final ExprlistStar exprlistStar;

    public ReturnStmt(
            boolean isTokenReturn,
            ExprlistStar exprlistStar
    ) {
        this.isTokenReturn = isTokenReturn;
        this.exprlistStar = exprlistStar;
    }

    public boolean getIsTokenReturn() {
        return isTokenReturn;
    }

    public Optional<ExprlistStar> getExprlistStar() {
        return Optional.ofNullable(exprlistStar);
    }
}

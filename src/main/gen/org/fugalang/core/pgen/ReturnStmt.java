package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.Optional;

// return_stmt: 'return' ['exprlist_star']
public final class ReturnStmt extends ConjunctionRule {
    public static final String RULE_NAME = "return_stmt";

    private final boolean isTokenReturn;
    private final ExprlistStar exprlistStar;

    public ReturnStmt(
            boolean isTokenReturn,
            ExprlistStar exprlistStar
    ) {
        this.isTokenReturn = isTokenReturn;
        this.exprlistStar = exprlistStar;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addRequired("isTokenReturn", isTokenReturn);
        addOptional("exprlistStar", exprlistStar);
    }

    public boolean isTokenReturn() {
        return isTokenReturn;
    }

    public Optional<ExprlistStar> exprlistStar() {
        return Optional.ofNullable(exprlistStar);
    }
}

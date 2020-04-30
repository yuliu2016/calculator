package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;

// continue_stmt: 'continue'
public final class ContinueStmt extends ConjunctionRule {
    private final boolean isTokenContinue;

    public ContinueStmt(
            boolean isTokenContinue
    ) {
        this.isTokenContinue = isTokenContinue;
    }

    @Override
    protected void buildRule() {
        addRequired("isTokenContinue", isTokenContinue);
    }

    public boolean isTokenContinue() {
        return isTokenContinue;
    }
}

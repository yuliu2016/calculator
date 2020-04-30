package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;

// continue_stmt: 'continue'
public final class ContinueStmt extends ConjunctionRule {
    private final boolean isTokenContinue;

    public ContinueStmt(
            boolean isTokenContinue
    ) {
        this.isTokenContinue = isTokenContinue;

        addRequired("isTokenContinue", isTokenContinue);
    }

    public boolean getIsTokenContinue() {
        return isTokenContinue;
    }
}

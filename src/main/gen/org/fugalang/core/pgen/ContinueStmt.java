package org.fugalang.core.pgen;

// continue_stmt: 'continue'
public class ContinueStmt {
    private final boolean isTokenContinue;

    public ContinueStmt(
            boolean isTokenContinue
    ) {
        this.isTokenContinue = isTokenContinue;
    }

    public boolean getIsTokenContinue() {
        return isTokenContinue;
    }
}

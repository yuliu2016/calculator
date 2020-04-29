package org.fugalang.core.pgen;

// continue_stmt: 'continue'
public class ContinueStmt {
    public final boolean isTokenContinue;

    public ContinueStmt(
            boolean isTokenContinue
    ) {
        this.isTokenContinue = isTokenContinue;
    }
}

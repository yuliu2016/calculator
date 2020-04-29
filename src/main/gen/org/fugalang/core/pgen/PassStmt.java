package org.fugalang.core.pgen;

// pass_stmt: 'pass'
public class PassStmt {
    private final boolean isTokenPass;

    public PassStmt(
            boolean isTokenPass
    ) {
        this.isTokenPass = isTokenPass;
    }

    public boolean getIsTokenPass() {
        return isTokenPass;
    }
}

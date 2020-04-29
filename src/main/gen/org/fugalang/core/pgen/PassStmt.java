package org.fugalang.core.pgen;

// pass_stmt: 'pass'
public class PassStmt {
    public final boolean isTokenPass;

    public PassStmt(
            boolean isTokenPass
    ) {
        this.isTokenPass = isTokenPass;
    }
}

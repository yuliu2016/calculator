package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;

// pass_stmt: 'pass'
public final class PassStmt extends ConjunctionRule {
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

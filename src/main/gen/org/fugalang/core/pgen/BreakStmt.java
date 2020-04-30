package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;

// break_stmt: 'break'
public final class BreakStmt extends ConjunctionRule {
    private final boolean isTokenBreak;

    public BreakStmt(
            boolean isTokenBreak
    ) {
        this.isTokenBreak = isTokenBreak;

        addRequired("isTokenBreak", isTokenBreak);
    }

    public boolean isTokenBreak() {
        return isTokenBreak;
    }
}

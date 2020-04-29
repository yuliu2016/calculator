package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;

// break_stmt: 'break'
public final class BreakStmt extends ConjunctionRule {
    private final boolean isTokenBreak;

    public BreakStmt(
            boolean isTokenBreak
    ) {
        this.isTokenBreak = isTokenBreak;
    }

    public boolean getIsTokenBreak() {
        return isTokenBreak;
    }
}

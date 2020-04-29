package org.fugalang.core.pgen;

// break_stmt: 'break'
public class BreakStmt {
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

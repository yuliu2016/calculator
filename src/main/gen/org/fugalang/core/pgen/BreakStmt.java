package org.fugalang.core.pgen;

// break_stmt: 'break'
public class BreakStmt {
    public final boolean isTokenBreak;

    public BreakStmt(
            boolean isTokenBreak
    ) {
        this.isTokenBreak = isTokenBreak;
    }
}

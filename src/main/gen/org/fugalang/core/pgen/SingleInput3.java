package org.fugalang.core.pgen;

// 'compound_stmt' 'NEWLINE'
public class SingleInput3 {
    public final CompoundStmt compoundStmt;
    public final Object newline;

    public SingleInput3(
            CompoundStmt compoundStmt,
            Object newline
    ) {
        this.compoundStmt = compoundStmt;
        this.newline = newline;
    }
}

package org.fugalang.core.pgen;

// 'simple_stmt' | 'compound_stmt'
public class Stmt1Group {
    public final SimpleStmt simpleStmt;
    public final CompoundStmt compoundStmt;

    public Stmt1Group(
            SimpleStmt simpleStmt,
            CompoundStmt compoundStmt
    ) {
        this.simpleStmt = simpleStmt;
        this.compoundStmt = compoundStmt;
    }
}

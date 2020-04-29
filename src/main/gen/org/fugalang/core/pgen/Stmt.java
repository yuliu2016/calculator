package org.fugalang.core.pgen;

// stmt: ('simple_stmt' | 'compound_stmt') 'NEWLINE'
public class Stmt {
    public final Stmt1Group stmt1Group;
    public final Object newline;

    public Stmt(
            Stmt1Group stmt1Group,
            Object newline
    ) {
        this.stmt1Group = stmt1Group;
        this.newline = newline;
    }

    // 'simple_stmt' | 'compound_stmt'
    public static class Stmt1Group {
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
}

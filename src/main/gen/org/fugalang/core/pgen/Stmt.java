package org.fugalang.core.pgen;

// stmt: ('simple_stmt' | 'compound_stmt') 'NEWLINE'
public class Stmt {
    private final Stmt1Group stmt1Group;
    private final Object newline;

    public Stmt(
            Stmt1Group stmt1Group,
            Object newline
    ) {
        this.stmt1Group = stmt1Group;
        this.newline = newline;
    }

    public Stmt1Group getStmt1Group() {
        return stmt1Group;
    }

    public Object getNewline() {
        return newline;
    }

    // 'simple_stmt' | 'compound_stmt'
    public static class Stmt1Group {
        private final SimpleStmt simpleStmt;
        private final CompoundStmt compoundStmt;

        public Stmt1Group(
                SimpleStmt simpleStmt,
                CompoundStmt compoundStmt
        ) {
            this.simpleStmt = simpleStmt;
            this.compoundStmt = compoundStmt;
        }

        public SimpleStmt getSimpleStmt() {
            return simpleStmt;
        }

        public CompoundStmt getCompoundStmt() {
            return compoundStmt;
        }
    }
}

package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import org.fugalang.core.parser.DisjunctionRule;

// stmt: ('simple_stmt' | 'compound_stmt') 'NEWLINE'
public final class Stmt extends ConjunctionRule {
    private final Stmt1Group stmt1Group;
    private final Object newline;

    public Stmt(
            Stmt1Group stmt1Group,
            Object newline
    ) {
        this.stmt1Group = stmt1Group;
        this.newline = newline;

        addRequired("stmt1Group", stmt1Group);
        addRequired("newline", newline);
    }

    public Stmt1Group stmt1Group() {
        return stmt1Group;
    }

    public Object newline() {
        return newline;
    }

    // 'simple_stmt' | 'compound_stmt'
    public static final class Stmt1Group extends DisjunctionRule {
        private final SimpleStmt simpleStmt;
        private final CompoundStmt compoundStmt;

        public Stmt1Group(
                SimpleStmt simpleStmt,
                CompoundStmt compoundStmt
        ) {
            this.simpleStmt = simpleStmt;
            this.compoundStmt = compoundStmt;

            addChoice("simpleStmt", simpleStmt);
            addChoice("compoundStmt", compoundStmt);
        }

        public SimpleStmt simpleStmt() {
            return simpleStmt;
        }

        public CompoundStmt compoundStmt() {
            return compoundStmt;
        }
    }
}

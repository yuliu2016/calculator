package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import org.fugalang.core.parser.DisjunctionRule;

// stmt: ('simple_stmt' | 'compound_stmt') 'NEWLINE'
public final class Stmt extends ConjunctionRule {
    private final Stmt1 stmt1;
    private final Object newline;

    public Stmt(
            Stmt1 stmt1,
            Object newline
    ) {
        this.stmt1 = stmt1;
        this.newline = newline;

        addRequired("stmt1", stmt1);
        addRequired("newline", newline);
    }

    public Stmt1 stmt1() {
        return stmt1;
    }

    public Object newline() {
        return newline;
    }

    // 'simple_stmt' | 'compound_stmt'
    public static final class Stmt1 extends DisjunctionRule {
        private final SimpleStmt simpleStmt;
        private final CompoundStmt compoundStmt;

        public Stmt1(
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

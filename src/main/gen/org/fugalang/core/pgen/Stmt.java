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
}

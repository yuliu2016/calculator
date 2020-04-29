package org.fugalang.core.pgen;

// compound_stmt: 'if_stmt' | 'while_stmt' | 'for_stmt' | 'try_stmt' | 'with_stmt'
public class CompoundStmt {
    public final IfStmt ifStmt;
    public final WhileStmt whileStmt;
    public final ForStmt forStmt;
    public final TryStmt tryStmt;
    public final WithStmt withStmt;

    public CompoundStmt(
            IfStmt ifStmt,
            WhileStmt whileStmt,
            ForStmt forStmt,
            TryStmt tryStmt,
            WithStmt withStmt
    ) {
        this.ifStmt = ifStmt;
        this.whileStmt = whileStmt;
        this.forStmt = forStmt;
        this.tryStmt = tryStmt;
        this.withStmt = withStmt;
    }
}

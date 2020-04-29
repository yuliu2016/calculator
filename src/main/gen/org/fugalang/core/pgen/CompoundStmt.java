package org.fugalang.core.pgen;

// compound_stmt: 'if_stmt' | 'while_stmt' | 'for_stmt' | 'try_stmt' | 'with_stmt'
public class CompoundStmt {
    private final IfStmt ifStmt;
    private final WhileStmt whileStmt;
    private final ForStmt forStmt;
    private final TryStmt tryStmt;
    private final WithStmt withStmt;

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

    public IfStmt getIfStmt() {
        return ifStmt;
    }

    public WhileStmt getWhileStmt() {
        return whileStmt;
    }

    public ForStmt getForStmt() {
        return forStmt;
    }

    public TryStmt getTryStmt() {
        return tryStmt;
    }

    public WithStmt getWithStmt() {
        return withStmt;
    }
}

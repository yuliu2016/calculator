package org.fugalang.core.pgen;

import org.fugalang.core.parser.DisjunctionRule;

// compound_stmt: 'if_stmt' | 'while_stmt' | 'for_stmt' | 'try_stmt' | 'with_stmt'
public final class CompoundStmt extends DisjunctionRule {
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

    @Override
    protected void buildRule() {
        addChoice("ifStmt", ifStmt);
        addChoice("whileStmt", whileStmt);
        addChoice("forStmt", forStmt);
        addChoice("tryStmt", tryStmt);
        addChoice("withStmt", withStmt);
    }

    public IfStmt ifStmt() {
        return ifStmt;
    }

    public WhileStmt whileStmt() {
        return whileStmt;
    }

    public ForStmt forStmt() {
        return forStmt;
    }

    public TryStmt tryStmt() {
        return tryStmt;
    }

    public WithStmt withStmt() {
        return withStmt;
    }
}

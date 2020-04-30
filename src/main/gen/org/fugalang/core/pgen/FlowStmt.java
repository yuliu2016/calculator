package org.fugalang.core.pgen;

import org.fugalang.core.parser.DisjunctionRule;

// flow_stmt: 'break_stmt' | 'continue_stmt' | 'return_stmt' | 'raise_stmt'
public final class FlowStmt extends DisjunctionRule {
    private final BreakStmt breakStmt;
    private final ContinueStmt continueStmt;
    private final ReturnStmt returnStmt;
    private final RaiseStmt raiseStmt;

    public FlowStmt(
            BreakStmt breakStmt,
            ContinueStmt continueStmt,
            ReturnStmt returnStmt,
            RaiseStmt raiseStmt
    ) {
        this.breakStmt = breakStmt;
        this.continueStmt = continueStmt;
        this.returnStmt = returnStmt;
        this.raiseStmt = raiseStmt;
    }

    @Override
    protected void buildRule() {
        addChoice("breakStmt", breakStmt);
        addChoice("continueStmt", continueStmt);
        addChoice("returnStmt", returnStmt);
        addChoice("raiseStmt", raiseStmt);
    }

    public BreakStmt breakStmt() {
        return breakStmt;
    }

    public ContinueStmt continueStmt() {
        return continueStmt;
    }

    public ReturnStmt returnStmt() {
        return returnStmt;
    }

    public RaiseStmt raiseStmt() {
        return raiseStmt;
    }
}

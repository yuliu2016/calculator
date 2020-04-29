package org.fugalang.core.pgen;

// flow_stmt: 'break_stmt' | 'continue_stmt' | 'return_stmt' | 'raise_stmt'
public class FlowStmt {
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

    public BreakStmt getBreakStmt() {
        return breakStmt;
    }

    public ContinueStmt getContinueStmt() {
        return continueStmt;
    }

    public ReturnStmt getReturnStmt() {
        return returnStmt;
    }

    public RaiseStmt getRaiseStmt() {
        return raiseStmt;
    }
}

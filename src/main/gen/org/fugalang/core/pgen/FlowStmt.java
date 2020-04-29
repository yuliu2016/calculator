package org.fugalang.core.pgen;

// flow_stmt: 'break_stmt' | 'continue_stmt' | 'return_stmt' | 'raise_stmt'
public class FlowStmt {
    public final BreakStmt breakStmt;
    public final ContinueStmt continueStmt;
    public final ReturnStmt returnStmt;
    public final RaiseStmt raiseStmt;

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
}

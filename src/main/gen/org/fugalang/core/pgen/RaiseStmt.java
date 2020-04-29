package org.fugalang.core.pgen;

// raise_stmt: 'raise' ['expr' ['from' 'expr']]
public class RaiseStmt {
    public final boolean isTokenRaise;
    public final RaiseStmt2Group raiseStmt2Group;

    public RaiseStmt(
            boolean isTokenRaise,
            RaiseStmt2Group raiseStmt2Group
    ) {
        this.isTokenRaise = isTokenRaise;
        this.raiseStmt2Group = raiseStmt2Group;
    }
}

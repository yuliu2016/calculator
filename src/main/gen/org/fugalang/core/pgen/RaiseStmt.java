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

    // 'expr' ['from' 'expr']
    public static class RaiseStmt2Group {
        public final Expr expr;
        public final RaiseStmt2Group2Group raiseStmt2Group2Group;

        public RaiseStmt2Group(
                Expr expr,
                RaiseStmt2Group2Group raiseStmt2Group2Group
        ) {
            this.expr = expr;
            this.raiseStmt2Group2Group = raiseStmt2Group2Group;
        }
    }

    // 'from' 'expr'
    public static class RaiseStmt2Group2Group {
        public final boolean isTokenFrom;
        public final Expr expr;

        public RaiseStmt2Group2Group(
                boolean isTokenFrom,
                Expr expr
        ) {
            this.isTokenFrom = isTokenFrom;
            this.expr = expr;
        }
    }
}

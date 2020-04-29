package org.fugalang.core.pgen;

// raise_stmt: 'raise' ['expr' ['from' 'expr']]
public class RaiseStmt {
    private final boolean isTokenRaise;
    private final RaiseStmt2Group raiseStmt2Group;

    public RaiseStmt(
            boolean isTokenRaise,
            RaiseStmt2Group raiseStmt2Group
    ) {
        this.isTokenRaise = isTokenRaise;
        this.raiseStmt2Group = raiseStmt2Group;
    }

    public boolean getIsTokenRaise() {
        return isTokenRaise;
    }

    public RaiseStmt2Group getRaiseStmt2Group() {
        return raiseStmt2Group;
    }

    // 'expr' ['from' 'expr']
    public static class RaiseStmt2Group {
        private final Expr expr;
        private final RaiseStmt2Group2Group raiseStmt2Group2Group;

        public RaiseStmt2Group(
                Expr expr,
                RaiseStmt2Group2Group raiseStmt2Group2Group
        ) {
            this.expr = expr;
            this.raiseStmt2Group2Group = raiseStmt2Group2Group;
        }

        public Expr getExpr() {
            return expr;
        }

        public RaiseStmt2Group2Group getRaiseStmt2Group2Group() {
            return raiseStmt2Group2Group;
        }
    }

    // 'from' 'expr'
    public static class RaiseStmt2Group2Group {
        private final boolean isTokenFrom;
        private final Expr expr;

        public RaiseStmt2Group2Group(
                boolean isTokenFrom,
                Expr expr
        ) {
            this.isTokenFrom = isTokenFrom;
            this.expr = expr;
        }

        public boolean getIsTokenFrom() {
            return isTokenFrom;
        }

        public Expr getExpr() {
            return expr;
        }
    }
}

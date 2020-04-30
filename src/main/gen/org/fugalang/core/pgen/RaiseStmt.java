package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.Optional;

// raise_stmt: 'raise' ['expr' ['from' 'expr']]
public final class RaiseStmt extends ConjunctionRule {
    private final boolean isTokenRaise;
    private final RaiseStmt2Group raiseStmt2Group;

    public RaiseStmt(
            boolean isTokenRaise,
            RaiseStmt2Group raiseStmt2Group
    ) {
        this.isTokenRaise = isTokenRaise;
        this.raiseStmt2Group = raiseStmt2Group;

        addRequired("isTokenRaise", isTokenRaise);
        addOptional("raiseStmt2Group", raiseStmt2Group);
    }

    public boolean getIsTokenRaise() {
        return isTokenRaise;
    }

    public Optional<RaiseStmt2Group> getRaiseStmt2Group() {
        return Optional.ofNullable(raiseStmt2Group);
    }

    // 'expr' ['from' 'expr']
    public static final class RaiseStmt2Group extends ConjunctionRule {
        private final Expr expr;
        private final RaiseStmt2Group2Group raiseStmt2Group2Group;

        public RaiseStmt2Group(
                Expr expr,
                RaiseStmt2Group2Group raiseStmt2Group2Group
        ) {
            this.expr = expr;
            this.raiseStmt2Group2Group = raiseStmt2Group2Group;

            addRequired("expr", expr);
            addOptional("raiseStmt2Group2Group", raiseStmt2Group2Group);
        }

        public Expr getExpr() {
            return expr;
        }

        public Optional<RaiseStmt2Group2Group> getRaiseStmt2Group2Group() {
            return Optional.ofNullable(raiseStmt2Group2Group);
        }
    }

    // 'from' 'expr'
    public static final class RaiseStmt2Group2Group extends ConjunctionRule {
        private final boolean isTokenFrom;
        private final Expr expr;

        public RaiseStmt2Group2Group(
                boolean isTokenFrom,
                Expr expr
        ) {
            this.isTokenFrom = isTokenFrom;
            this.expr = expr;

            addRequired("isTokenFrom", isTokenFrom);
            addRequired("expr", expr);
        }

        public boolean getIsTokenFrom() {
            return isTokenFrom;
        }

        public Expr getExpr() {
            return expr;
        }
    }
}

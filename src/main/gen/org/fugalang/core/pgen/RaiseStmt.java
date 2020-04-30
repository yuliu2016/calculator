package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.Optional;

// raise_stmt: 'raise' ['expr' ['from' 'expr']]
public final class RaiseStmt extends ConjunctionRule {
    private final boolean isTokenRaise;
    private final RaiseStmt2 raiseStmt2;

    public RaiseStmt(
            boolean isTokenRaise,
            RaiseStmt2 raiseStmt2
    ) {
        this.isTokenRaise = isTokenRaise;
        this.raiseStmt2 = raiseStmt2;
    }

    @Override
    protected void buildRule() {
        addRequired("isTokenRaise", isTokenRaise);
        addOptional("raiseStmt2", raiseStmt2);
    }

    public boolean isTokenRaise() {
        return isTokenRaise;
    }

    public Optional<RaiseStmt2> raiseStmt2() {
        return Optional.ofNullable(raiseStmt2);
    }

    // 'expr' ['from' 'expr']
    public static final class RaiseStmt2 extends ConjunctionRule {
        private final Expr expr;
        private final RaiseStmt22 raiseStmt22;

        public RaiseStmt2(
                Expr expr,
                RaiseStmt22 raiseStmt22
        ) {
            this.expr = expr;
            this.raiseStmt22 = raiseStmt22;
        }

        @Override
        protected void buildRule() {
            addRequired("expr", expr);
            addOptional("raiseStmt22", raiseStmt22);
        }

        public Expr expr() {
            return expr;
        }

        public Optional<RaiseStmt22> raiseStmt22() {
            return Optional.ofNullable(raiseStmt22);
        }
    }

    // 'from' 'expr'
    public static final class RaiseStmt22 extends ConjunctionRule {
        private final boolean isTokenFrom;
        private final Expr expr;

        public RaiseStmt22(
                boolean isTokenFrom,
                Expr expr
        ) {
            this.isTokenFrom = isTokenFrom;
            this.expr = expr;
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenFrom", isTokenFrom);
            addRequired("expr", expr);
        }

        public boolean isTokenFrom() {
            return isTokenFrom;
        }

        public Expr expr() {
            return expr;
        }
    }
}

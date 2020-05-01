package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import org.fugalang.core.parser.ParseTree;

import java.util.Optional;

/**
 * raise_stmt: 'raise' ['expr' ['from' 'expr']]
 */
public final class RaiseStmt extends ConjunctionRule {
    public static final String RULE_NAME = "raise_stmt";

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
        setExplicitName(RULE_NAME);
        addRequired("isTokenRaise", isTokenRaise);
        addOptional("raiseStmt2", raiseStmt2);
    }

    public boolean isTokenRaise() {
        return isTokenRaise;
    }

    public Optional<RaiseStmt2> raiseStmt2() {
        return Optional.ofNullable(raiseStmt2);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        boolean result;

        result = parseTree.consumeTokenLiteral("raise");
        RaiseStmt2.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * 'expr' ['from' 'expr']
     */
    public static final class RaiseStmt2 extends ConjunctionRule {
        public static final String RULE_NAME = "raise_stmt:2";

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
            setImpliedName(RULE_NAME);
            addRequired("expr", expr);
            addOptional("raiseStmt22", raiseStmt22);
        }

        public Expr expr() {
            return expr;
        }

        public Optional<RaiseStmt22> raiseStmt22() {
            return Optional.ofNullable(raiseStmt22);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            result = Expr.parse(parseTree, level + 1);
            RaiseStmt22.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * 'from' 'expr'
     */
    public static final class RaiseStmt22 extends ConjunctionRule {
        public static final String RULE_NAME = "raise_stmt:2:2";

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
            setImpliedName(RULE_NAME);
            addRequired("isTokenFrom", isTokenFrom);
            addRequired("expr", expr);
        }

        public boolean isTokenFrom() {
            return isTokenFrom;
        }

        public Expr expr() {
            return expr;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            result = parseTree.consumeTokenLiteral("from");
            result = result && Expr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}

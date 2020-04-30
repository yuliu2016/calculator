package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.Optional;

// assert_stmt: 'assert' 'expr' [',' 'expr']
public final class AssertStmt extends ConjunctionRule {
    public static final String RULE_NAME = "assert_stmt";

    private final boolean isTokenAssert;
    private final Expr expr;
    private final AssertStmt3 assertStmt3;

    public AssertStmt(
            boolean isTokenAssert,
            Expr expr,
            AssertStmt3 assertStmt3
    ) {
        this.isTokenAssert = isTokenAssert;
        this.expr = expr;
        this.assertStmt3 = assertStmt3;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addRequired("isTokenAssert", isTokenAssert);
        addRequired("expr", expr);
        addOptional("assertStmt3", assertStmt3);
    }

    public boolean isTokenAssert() {
        return isTokenAssert;
    }

    public Expr expr() {
        return expr;
    }

    public Optional<AssertStmt3> assertStmt3() {
        return Optional.ofNullable(assertStmt3);
    }

    // ',' 'expr'
    public static final class AssertStmt3 extends ConjunctionRule {
        public static final String RULE_NAME = "assert_stmt:3";

        private final boolean isTokenComma;
        private final Expr expr;

        public AssertStmt3(
                boolean isTokenComma,
                Expr expr
        ) {
            this.isTokenComma = isTokenComma;
            this.expr = expr;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("isTokenComma", isTokenComma);
            addRequired("expr", expr);
        }

        public boolean isTokenComma() {
            return isTokenComma;
        }

        public Expr expr() {
            return expr;
        }
    }
}

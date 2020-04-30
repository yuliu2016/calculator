package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.Optional;

// assert_stmt: 'assert' 'expr' [',' 'expr']
public final class AssertStmt extends ConjunctionRule {
    private final boolean isTokenAssert;
    private final Expr expr;
    private final AssertStmt3Group assertStmt3Group;

    public AssertStmt(
            boolean isTokenAssert,
            Expr expr,
            AssertStmt3Group assertStmt3Group
    ) {
        this.isTokenAssert = isTokenAssert;
        this.expr = expr;
        this.assertStmt3Group = assertStmt3Group;

        addRequired("isTokenAssert", isTokenAssert);
        addRequired("expr", expr);
        addOptional("assertStmt3Group", assertStmt3Group);
    }

    public boolean isTokenAssert() {
        return isTokenAssert;
    }

    public Expr expr() {
        return expr;
    }

    public Optional<AssertStmt3Group> assertStmt3Group() {
        return Optional.ofNullable(assertStmt3Group);
    }

    // ',' 'expr'
    public static final class AssertStmt3Group extends ConjunctionRule {
        private final boolean isTokenComma;
        private final Expr expr;

        public AssertStmt3Group(
                boolean isTokenComma,
                Expr expr
        ) {
            this.isTokenComma = isTokenComma;
            this.expr = expr;

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

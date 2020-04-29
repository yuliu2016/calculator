package org.fugalang.core.pgen;

// assert_stmt: 'assert' 'expr' [',' 'expr']
public class AssertStmt {
    public final boolean isTokenAssert;
    public final Expr expr;
    public final AssertStmt3Group assertStmt3Group;

    public AssertStmt(
            boolean isTokenAssert,
            Expr expr,
            AssertStmt3Group assertStmt3Group
    ) {
        this.isTokenAssert = isTokenAssert;
        this.expr = expr;
        this.assertStmt3Group = assertStmt3Group;
    }

    // ',' 'expr'
    public static class AssertStmt3Group {
        public final boolean isTokenComma;
        public final Expr expr;

        public AssertStmt3Group(
                boolean isTokenComma,
                Expr expr
        ) {
            this.isTokenComma = isTokenComma;
            this.expr = expr;
        }
    }
}

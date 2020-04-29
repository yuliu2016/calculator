package org.fugalang.core.pgen;

// ',' 'expr'
public class AssertStmt3Group {
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

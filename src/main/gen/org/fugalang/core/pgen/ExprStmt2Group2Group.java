package org.fugalang.core.pgen;

// '=' 'exprlist_star'
public class ExprStmt2Group2Group {
    public final boolean isTokenAssign;
    public final ExprlistStar exprlistStar;

    public ExprStmt2Group2Group(
            boolean isTokenAssign,
            ExprlistStar exprlistStar
    ) {
        this.isTokenAssign = isTokenAssign;
        this.exprlistStar = exprlistStar;
    }
}

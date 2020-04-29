package org.fugalang.core.pgen;

// return_stmt: 'return' ['exprlist_star']
public class ReturnStmt {
    public final boolean isTokenReturn;
    public final ExprlistStar exprlistStar;

    public ReturnStmt(
            boolean isTokenReturn,
            ExprlistStar exprlistStar
    ) {
        this.isTokenReturn = isTokenReturn;
        this.exprlistStar = exprlistStar;
    }
}

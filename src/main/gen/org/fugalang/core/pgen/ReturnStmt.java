package org.fugalang.core.pgen;

// return_stmt: 'return' ['exprlist_star']
public class ReturnStmt {
    private final boolean isTokenReturn;
    private final ExprlistStar exprlistStar;

    public ReturnStmt(
            boolean isTokenReturn,
            ExprlistStar exprlistStar
    ) {
        this.isTokenReturn = isTokenReturn;
        this.exprlistStar = exprlistStar;
    }

    public boolean getIsTokenReturn() {
        return isTokenReturn;
    }

    public ExprlistStar getExprlistStar() {
        return exprlistStar;
    }
}

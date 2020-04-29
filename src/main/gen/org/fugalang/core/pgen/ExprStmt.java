package org.fugalang.core.pgen;

// expr_stmt: 'exprlist_star' ['augassign' 'exprlist' | ('=' 'exprlist_star')*]
public class ExprStmt {
    public final ExprlistStar exprlistStar;
    public final ExprStmt2Group exprStmt2Group;

    public ExprStmt(
            ExprlistStar exprlistStar,
            ExprStmt2Group exprStmt2Group
    ) {
        this.exprlistStar = exprlistStar;
        this.exprStmt2Group = exprStmt2Group;
    }
}

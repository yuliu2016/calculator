package org.fugalang.core.pgen;

// 'augassign' 'exprlist' | ('=' 'exprlist_star')*
public class ExprStmt2Group {
    public final ExprStmt2Group1 exprStmt2Group1;
    public final ExprStmt2Group2Group exprStmt2Group2Group;

    public ExprStmt2Group(
            ExprStmt2Group1 exprStmt2Group1,
            ExprStmt2Group2Group exprStmt2Group2Group
    ) {
        this.exprStmt2Group1 = exprStmt2Group1;
        this.exprStmt2Group2Group = exprStmt2Group2Group;
    }
}

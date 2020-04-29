package org.fugalang.core.pgen;

// 'augassign' 'exprlist'
public class ExprStmt2Group1 {
    public final Augassign augassign;
    public final Exprlist exprlist;

    public ExprStmt2Group1(
            Augassign augassign,
            Exprlist exprlist
    ) {
        this.augassign = augassign;
        this.exprlist = exprlist;
    }
}

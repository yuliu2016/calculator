package org.fugalang.core.pgen;

import java.util.List;

// 'augassign' 'exprlist' | ('=' 'exprlist_star')*
public class ExprStmt2Group {
    public final ExprStmt2Group1 exprStmt2Group1;
    public final List<ExprStmt2Group2Group> exprStmt2Group2GroupList;

    public ExprStmt2Group(
            ExprStmt2Group1 exprStmt2Group1,
            List<ExprStmt2Group2Group> exprStmt2Group2GroupList
    ) {
        this.exprStmt2Group1 = exprStmt2Group1;
        this.exprStmt2Group2GroupList = exprStmt2Group2GroupList;
    }
}

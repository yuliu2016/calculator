package org.fugalang.core.pgen;

import java.util.List;

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

    // 'augassign' 'exprlist' | ('=' 'exprlist_star')*
    public static class ExprStmt2Group {
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

    // 'augassign' 'exprlist'
    public static class ExprStmt2Group1 {
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

    // '=' 'exprlist_star'
    public static class ExprStmt2Group2Group {
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
}

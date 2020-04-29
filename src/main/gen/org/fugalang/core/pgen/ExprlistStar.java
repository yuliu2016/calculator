package org.fugalang.core.pgen;

import java.util.List;

// exprlist_star: ('expr' | 'star_expr') (',' ('expr' | 'star_expr'))* [',']
public class ExprlistStar {
    public final ExprlistStar1Group exprlistStar1Group;
    public final List<ExprlistStar2Group> exprlistStar2GroupList;
    public final boolean isTokenComma;

    public ExprlistStar(
            ExprlistStar1Group exprlistStar1Group,
            List<ExprlistStar2Group> exprlistStar2GroupList,
            boolean isTokenComma
    ) {
        this.exprlistStar1Group = exprlistStar1Group;
        this.exprlistStar2GroupList = exprlistStar2GroupList;
        this.isTokenComma = isTokenComma;
    }

    // 'expr' | 'star_expr'
    public static class ExprlistStar1Group {
        public final Expr expr;
        public final StarExpr starExpr;

        public ExprlistStar1Group(
                Expr expr,
                StarExpr starExpr
        ) {
            this.expr = expr;
            this.starExpr = starExpr;
        }
    }

    // ',' ('expr' | 'star_expr')
    public static class ExprlistStar2Group {
        public final boolean isTokenComma;
        public final ExprlistStar2Group2Group exprlistStar2Group2Group;

        public ExprlistStar2Group(
                boolean isTokenComma,
                ExprlistStar2Group2Group exprlistStar2Group2Group
        ) {
            this.isTokenComma = isTokenComma;
            this.exprlistStar2Group2Group = exprlistStar2Group2Group;
        }
    }

    // 'expr' | 'star_expr'
    public static class ExprlistStar2Group2Group {
        public final Expr expr;
        public final StarExpr starExpr;

        public ExprlistStar2Group2Group(
                Expr expr,
                StarExpr starExpr
        ) {
            this.expr = expr;
            this.starExpr = starExpr;
        }
    }
}

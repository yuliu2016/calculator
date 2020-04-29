package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;
import org.fugalang.core.parser.DisjunctionRule;

// exprlist_star: ('expr' | 'star_expr') (',' ('expr' | 'star_expr'))* [',']
public final class ExprlistStar extends ConjunctionRule {
    private final ExprlistStar1Group exprlistStar1Group;
    private final List<ExprlistStar2Group> exprlistStar2GroupList;
    private final boolean isTokenComma;

    public ExprlistStar(
            ExprlistStar1Group exprlistStar1Group,
            List<ExprlistStar2Group> exprlistStar2GroupList,
            boolean isTokenComma
    ) {
        this.exprlistStar1Group = exprlistStar1Group;
        this.exprlistStar2GroupList = exprlistStar2GroupList;
        this.isTokenComma = isTokenComma;
    }

    public ExprlistStar1Group getExprlistStar1Group() {
        return exprlistStar1Group;
    }

    public List<ExprlistStar2Group> getExprlistStar2GroupList() {
        return exprlistStar2GroupList;
    }

    public boolean getIsTokenComma() {
        return isTokenComma;
    }

    // 'expr' | 'star_expr'
    public static final class ExprlistStar1Group extends DisjunctionRule {
        private final Expr expr;
        private final StarExpr starExpr;

        public ExprlistStar1Group(
                Expr expr,
                StarExpr starExpr
        ) {
            this.expr = expr;
            this.starExpr = starExpr;
        }

        public Expr getExpr() {
            return expr;
        }

        public StarExpr getStarExpr() {
            return starExpr;
        }
    }

    // ',' ('expr' | 'star_expr')
    public static final class ExprlistStar2Group extends ConjunctionRule {
        private final boolean isTokenComma;
        private final ExprlistStar2Group2Group exprlistStar2Group2Group;

        public ExprlistStar2Group(
                boolean isTokenComma,
                ExprlistStar2Group2Group exprlistStar2Group2Group
        ) {
            this.isTokenComma = isTokenComma;
            this.exprlistStar2Group2Group = exprlistStar2Group2Group;
        }

        public boolean getIsTokenComma() {
            return isTokenComma;
        }

        public ExprlistStar2Group2Group getExprlistStar2Group2Group() {
            return exprlistStar2Group2Group;
        }
    }

    // 'expr' | 'star_expr'
    public static final class ExprlistStar2Group2Group extends DisjunctionRule {
        private final Expr expr;
        private final StarExpr starExpr;

        public ExprlistStar2Group2Group(
                Expr expr,
                StarExpr starExpr
        ) {
            this.expr = expr;
            this.starExpr = starExpr;
        }

        public Expr getExpr() {
            return expr;
        }

        public StarExpr getStarExpr() {
            return starExpr;
        }
    }
}

package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;
import org.fugalang.core.parser.DisjunctionRule;

// exprlist_comp: ('namedexpr_expr' | 'star_expr') ('comp_for' | (',' ('namedexpr_expr' | 'star_expr'))* [','])
public final class ExprlistComp extends ConjunctionRule {
    private final ExprlistComp1Group exprlistComp1Group;
    private final ExprlistComp2Group exprlistComp2Group;

    public ExprlistComp(
            ExprlistComp1Group exprlistComp1Group,
            ExprlistComp2Group exprlistComp2Group
    ) {
        this.exprlistComp1Group = exprlistComp1Group;
        this.exprlistComp2Group = exprlistComp2Group;
    }

    public ExprlistComp1Group getExprlistComp1Group() {
        return exprlistComp1Group;
    }

    public ExprlistComp2Group getExprlistComp2Group() {
        return exprlistComp2Group;
    }

    // 'namedexpr_expr' | 'star_expr'
    public static final class ExprlistComp1Group extends DisjunctionRule {
        private final NamedexprExpr namedexprExpr;
        private final StarExpr starExpr;

        public ExprlistComp1Group(
                NamedexprExpr namedexprExpr,
                StarExpr starExpr
        ) {
            this.namedexprExpr = namedexprExpr;
            this.starExpr = starExpr;
        }

        public NamedexprExpr getNamedexprExpr() {
            return namedexprExpr;
        }

        public StarExpr getStarExpr() {
            return starExpr;
        }
    }

    // 'comp_for' | (',' ('namedexpr_expr' | 'star_expr'))* [',']
    public static final class ExprlistComp2Group extends DisjunctionRule {
        private final CompFor compFor;
        private final ExprlistComp2Group2 exprlistComp2Group2;

        public ExprlistComp2Group(
                CompFor compFor,
                ExprlistComp2Group2 exprlistComp2Group2
        ) {
            this.compFor = compFor;
            this.exprlistComp2Group2 = exprlistComp2Group2;
        }

        public CompFor getCompFor() {
            return compFor;
        }

        public ExprlistComp2Group2 getExprlistComp2Group2() {
            return exprlistComp2Group2;
        }
    }

    // (',' ('namedexpr_expr' | 'star_expr'))* [',']
    public static final class ExprlistComp2Group2 extends ConjunctionRule {
        private final List<ExprlistComp2Group21Group> exprlistComp2Group21GroupList;
        private final boolean isTokenComma;

        public ExprlistComp2Group2(
                List<ExprlistComp2Group21Group> exprlistComp2Group21GroupList,
                boolean isTokenComma
        ) {
            this.exprlistComp2Group21GroupList = exprlistComp2Group21GroupList;
            this.isTokenComma = isTokenComma;
        }

        public List<ExprlistComp2Group21Group> getExprlistComp2Group21GroupList() {
            return exprlistComp2Group21GroupList;
        }

        public boolean getIsTokenComma() {
            return isTokenComma;
        }
    }

    // ',' ('namedexpr_expr' | 'star_expr')
    public static final class ExprlistComp2Group21Group extends ConjunctionRule {
        private final boolean isTokenComma;
        private final ExprlistComp2Group21Group2Group exprlistComp2Group21Group2Group;

        public ExprlistComp2Group21Group(
                boolean isTokenComma,
                ExprlistComp2Group21Group2Group exprlistComp2Group21Group2Group
        ) {
            this.isTokenComma = isTokenComma;
            this.exprlistComp2Group21Group2Group = exprlistComp2Group21Group2Group;
        }

        public boolean getIsTokenComma() {
            return isTokenComma;
        }

        public ExprlistComp2Group21Group2Group getExprlistComp2Group21Group2Group() {
            return exprlistComp2Group21Group2Group;
        }
    }

    // 'namedexpr_expr' | 'star_expr'
    public static final class ExprlistComp2Group21Group2Group extends DisjunctionRule {
        private final NamedexprExpr namedexprExpr;
        private final StarExpr starExpr;

        public ExprlistComp2Group21Group2Group(
                NamedexprExpr namedexprExpr,
                StarExpr starExpr
        ) {
            this.namedexprExpr = namedexprExpr;
            this.starExpr = starExpr;
        }

        public NamedexprExpr getNamedexprExpr() {
            return namedexprExpr;
        }

        public StarExpr getStarExpr() {
            return starExpr;
        }
    }
}

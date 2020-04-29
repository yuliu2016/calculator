package org.fugalang.core.pgen;

import java.util.List;

// exprlist_comp: ('namedexpr_expr' | 'star_expr') ('comp_for' | (',' ('namedexpr_expr' | 'star_expr'))* [','])
public class ExprlistComp {
    public final ExprlistComp1Group exprlistComp1Group;
    public final ExprlistComp2Group exprlistComp2Group;

    public ExprlistComp(
            ExprlistComp1Group exprlistComp1Group,
            ExprlistComp2Group exprlistComp2Group
    ) {
        this.exprlistComp1Group = exprlistComp1Group;
        this.exprlistComp2Group = exprlistComp2Group;
    }

    // 'namedexpr_expr' | 'star_expr'
    public static class ExprlistComp1Group {
        public final NamedexprExpr namedexprExpr;
        public final StarExpr starExpr;

        public ExprlistComp1Group(
                NamedexprExpr namedexprExpr,
                StarExpr starExpr
        ) {
            this.namedexprExpr = namedexprExpr;
            this.starExpr = starExpr;
        }
    }

    // 'comp_for' | (',' ('namedexpr_expr' | 'star_expr'))* [',']
    public static class ExprlistComp2Group {
        public final CompFor compFor;
        public final ExprlistComp2Group2 exprlistComp2Group2;

        public ExprlistComp2Group(
                CompFor compFor,
                ExprlistComp2Group2 exprlistComp2Group2
        ) {
            this.compFor = compFor;
            this.exprlistComp2Group2 = exprlistComp2Group2;
        }
    }

    // (',' ('namedexpr_expr' | 'star_expr'))* [',']
    public static class ExprlistComp2Group2 {
        public final List<ExprlistComp2Group21Group> exprlistComp2Group21GroupList;
        public final boolean isTokenComma;

        public ExprlistComp2Group2(
                List<ExprlistComp2Group21Group> exprlistComp2Group21GroupList,
                boolean isTokenComma
        ) {
            this.exprlistComp2Group21GroupList = exprlistComp2Group21GroupList;
            this.isTokenComma = isTokenComma;
        }
    }

    // ',' ('namedexpr_expr' | 'star_expr')
    public static class ExprlistComp2Group21Group {
        public final boolean isTokenComma;
        public final ExprlistComp2Group21Group2Group exprlistComp2Group21Group2Group;

        public ExprlistComp2Group21Group(
                boolean isTokenComma,
                ExprlistComp2Group21Group2Group exprlistComp2Group21Group2Group
        ) {
            this.isTokenComma = isTokenComma;
            this.exprlistComp2Group21Group2Group = exprlistComp2Group21Group2Group;
        }
    }

    // 'namedexpr_expr' | 'star_expr'
    public static class ExprlistComp2Group21Group2Group {
        public final NamedexprExpr namedexprExpr;
        public final StarExpr starExpr;

        public ExprlistComp2Group21Group2Group(
                NamedexprExpr namedexprExpr,
                StarExpr starExpr
        ) {
            this.namedexprExpr = namedexprExpr;
            this.starExpr = starExpr;
        }
    }
}

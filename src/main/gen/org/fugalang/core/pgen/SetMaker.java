package org.fugalang.core.pgen;

import java.util.List;

// set_maker: ('expr' | 'star_expr') ('comp_for' | (',' ('expr' | 'star_expr'))* [','])
public class SetMaker {
    public final SetMaker1Group setMaker1Group;
    public final SetMaker2Group setMaker2Group;

    public SetMaker(
            SetMaker1Group setMaker1Group,
            SetMaker2Group setMaker2Group
    ) {
        this.setMaker1Group = setMaker1Group;
        this.setMaker2Group = setMaker2Group;
    }

    // 'expr' | 'star_expr'
    public static class SetMaker1Group {
        public final Expr expr;
        public final StarExpr starExpr;

        public SetMaker1Group(
                Expr expr,
                StarExpr starExpr
        ) {
            this.expr = expr;
            this.starExpr = starExpr;
        }
    }

    // 'comp_for' | (',' ('expr' | 'star_expr'))* [',']
    public static class SetMaker2Group {
        public final CompFor compFor;
        public final SetMaker2Group2 setMaker2Group2;

        public SetMaker2Group(
                CompFor compFor,
                SetMaker2Group2 setMaker2Group2
        ) {
            this.compFor = compFor;
            this.setMaker2Group2 = setMaker2Group2;
        }
    }

    // (',' ('expr' | 'star_expr'))* [',']
    public static class SetMaker2Group2 {
        public final List<SetMaker2Group21Group> setMaker2Group21GroupList;
        public final boolean isTokenComma;

        public SetMaker2Group2(
                List<SetMaker2Group21Group> setMaker2Group21GroupList,
                boolean isTokenComma
        ) {
            this.setMaker2Group21GroupList = setMaker2Group21GroupList;
            this.isTokenComma = isTokenComma;
        }
    }

    // ',' ('expr' | 'star_expr')
    public static class SetMaker2Group21Group {
        public final boolean isTokenComma;
        public final SetMaker2Group21Group2Group setMaker2Group21Group2Group;

        public SetMaker2Group21Group(
                boolean isTokenComma,
                SetMaker2Group21Group2Group setMaker2Group21Group2Group
        ) {
            this.isTokenComma = isTokenComma;
            this.setMaker2Group21Group2Group = setMaker2Group21Group2Group;
        }
    }

    // 'expr' | 'star_expr'
    public static class SetMaker2Group21Group2Group {
        public final Expr expr;
        public final StarExpr starExpr;

        public SetMaker2Group21Group2Group(
                Expr expr,
                StarExpr starExpr
        ) {
            this.expr = expr;
            this.starExpr = starExpr;
        }
    }
}

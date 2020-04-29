package org.fugalang.core.pgen;

import java.util.List;

// set_maker: ('expr' | 'star_expr') ('comp_for' | (',' ('expr' | 'star_expr'))* [','])
public class SetMaker {
    private final SetMaker1Group setMaker1Group;
    private final SetMaker2Group setMaker2Group;

    public SetMaker(
            SetMaker1Group setMaker1Group,
            SetMaker2Group setMaker2Group
    ) {
        this.setMaker1Group = setMaker1Group;
        this.setMaker2Group = setMaker2Group;
    }

    public SetMaker1Group getSetMaker1Group() {
        return setMaker1Group;
    }

    public SetMaker2Group getSetMaker2Group() {
        return setMaker2Group;
    }

    // 'expr' | 'star_expr'
    public static class SetMaker1Group {
        private final Expr expr;
        private final StarExpr starExpr;

        public SetMaker1Group(
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

    // 'comp_for' | (',' ('expr' | 'star_expr'))* [',']
    public static class SetMaker2Group {
        private final CompFor compFor;
        private final SetMaker2Group2 setMaker2Group2;

        public SetMaker2Group(
                CompFor compFor,
                SetMaker2Group2 setMaker2Group2
        ) {
            this.compFor = compFor;
            this.setMaker2Group2 = setMaker2Group2;
        }

        public CompFor getCompFor() {
            return compFor;
        }

        public SetMaker2Group2 getSetMaker2Group2() {
            return setMaker2Group2;
        }
    }

    // (',' ('expr' | 'star_expr'))* [',']
    public static class SetMaker2Group2 {
        private final List<SetMaker2Group21Group> setMaker2Group21GroupList;
        private final boolean isTokenComma;

        public SetMaker2Group2(
                List<SetMaker2Group21Group> setMaker2Group21GroupList,
                boolean isTokenComma
        ) {
            this.setMaker2Group21GroupList = setMaker2Group21GroupList;
            this.isTokenComma = isTokenComma;
        }

        public List<SetMaker2Group21Group> getSetMaker2Group21GroupList() {
            return setMaker2Group21GroupList;
        }

        public boolean getIsTokenComma() {
            return isTokenComma;
        }
    }

    // ',' ('expr' | 'star_expr')
    public static class SetMaker2Group21Group {
        private final boolean isTokenComma;
        private final SetMaker2Group21Group2Group setMaker2Group21Group2Group;

        public SetMaker2Group21Group(
                boolean isTokenComma,
                SetMaker2Group21Group2Group setMaker2Group21Group2Group
        ) {
            this.isTokenComma = isTokenComma;
            this.setMaker2Group21Group2Group = setMaker2Group21Group2Group;
        }

        public boolean getIsTokenComma() {
            return isTokenComma;
        }

        public SetMaker2Group21Group2Group getSetMaker2Group21Group2Group() {
            return setMaker2Group21Group2Group;
        }
    }

    // 'expr' | 'star_expr'
    public static class SetMaker2Group21Group2Group {
        private final Expr expr;
        private final StarExpr starExpr;

        public SetMaker2Group21Group2Group(
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

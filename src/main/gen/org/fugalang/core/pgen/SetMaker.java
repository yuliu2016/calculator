package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;
import org.fugalang.core.parser.DisjunctionRule;

// set_maker: ('expr' | 'star_expr') ('comp_for' | (',' ('expr' | 'star_expr'))* [','])
public final class SetMaker extends ConjunctionRule {
    private final SetMaker1Group setMaker1Group;
    private final SetMaker2Group setMaker2Group;

    public SetMaker(
            SetMaker1Group setMaker1Group,
            SetMaker2Group setMaker2Group
    ) {
        this.setMaker1Group = setMaker1Group;
        this.setMaker2Group = setMaker2Group;

        addRequired("setMaker1Group", setMaker1Group);
        addRequired("setMaker2Group", setMaker2Group);
    }

    public SetMaker1Group getSetMaker1Group() {
        return setMaker1Group;
    }

    public SetMaker2Group getSetMaker2Group() {
        return setMaker2Group;
    }

    // 'expr' | 'star_expr'
    public static final class SetMaker1Group extends DisjunctionRule {
        private final Expr expr;
        private final StarExpr starExpr;

        public SetMaker1Group(
                Expr expr,
                StarExpr starExpr
        ) {
            this.expr = expr;
            this.starExpr = starExpr;

            addChoice("expr", expr);
            addChoice("starExpr", starExpr);
        }

        public Expr getExpr() {
            return expr;
        }

        public StarExpr getStarExpr() {
            return starExpr;
        }
    }

    // 'comp_for' | (',' ('expr' | 'star_expr'))* [',']
    public static final class SetMaker2Group extends DisjunctionRule {
        private final CompFor compFor;
        private final SetMaker2Group2 setMaker2Group2;

        public SetMaker2Group(
                CompFor compFor,
                SetMaker2Group2 setMaker2Group2
        ) {
            this.compFor = compFor;
            this.setMaker2Group2 = setMaker2Group2;

            addChoice("compFor", compFor);
            addChoice("setMaker2Group2", setMaker2Group2);
        }

        public CompFor getCompFor() {
            return compFor;
        }

        public SetMaker2Group2 getSetMaker2Group2() {
            return setMaker2Group2;
        }
    }

    // (',' ('expr' | 'star_expr'))* [',']
    public static final class SetMaker2Group2 extends ConjunctionRule {
        private final List<SetMaker2Group21Group> setMaker2Group21GroupList;
        private final boolean isTokenComma;

        public SetMaker2Group2(
                List<SetMaker2Group21Group> setMaker2Group21GroupList,
                boolean isTokenComma
        ) {
            this.setMaker2Group21GroupList = setMaker2Group21GroupList;
            this.isTokenComma = isTokenComma;

            addRequired("setMaker2Group21GroupList", setMaker2Group21GroupList);
            addRequired("isTokenComma", isTokenComma);
        }

        public List<SetMaker2Group21Group> getSetMaker2Group21GroupList() {
            return setMaker2Group21GroupList;
        }

        public boolean getIsTokenComma() {
            return isTokenComma;
        }
    }

    // ',' ('expr' | 'star_expr')
    public static final class SetMaker2Group21Group extends ConjunctionRule {
        private final boolean isTokenComma;
        private final SetMaker2Group21Group2Group setMaker2Group21Group2Group;

        public SetMaker2Group21Group(
                boolean isTokenComma,
                SetMaker2Group21Group2Group setMaker2Group21Group2Group
        ) {
            this.isTokenComma = isTokenComma;
            this.setMaker2Group21Group2Group = setMaker2Group21Group2Group;

            addRequired("isTokenComma", isTokenComma);
            addRequired("setMaker2Group21Group2Group", setMaker2Group21Group2Group);
        }

        public boolean getIsTokenComma() {
            return isTokenComma;
        }

        public SetMaker2Group21Group2Group getSetMaker2Group21Group2Group() {
            return setMaker2Group21Group2Group;
        }
    }

    // 'expr' | 'star_expr'
    public static final class SetMaker2Group21Group2Group extends DisjunctionRule {
        private final Expr expr;
        private final StarExpr starExpr;

        public SetMaker2Group21Group2Group(
                Expr expr,
                StarExpr starExpr
        ) {
            this.expr = expr;
            this.starExpr = starExpr;

            addChoice("expr", expr);
            addChoice("starExpr", starExpr);
        }

        public Expr getExpr() {
            return expr;
        }

        public StarExpr getStarExpr() {
            return starExpr;
        }
    }
}

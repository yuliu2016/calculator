package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;
import org.fugalang.core.parser.DisjunctionRule;

// exprlist_comp: 'expr_or_star' ('comp_for' | (',' 'expr_or_star')* [','])
public final class ExprlistComp extends ConjunctionRule {
    private final ExprOrStar exprOrStar;
    private final ExprlistComp2Group exprlistComp2Group;

    public ExprlistComp(
            ExprOrStar exprOrStar,
            ExprlistComp2Group exprlistComp2Group
    ) {
        this.exprOrStar = exprOrStar;
        this.exprlistComp2Group = exprlistComp2Group;

        addRequired("exprOrStar", exprOrStar);
        addRequired("exprlistComp2Group", exprlistComp2Group);
    }

    public ExprOrStar exprOrStar() {
        return exprOrStar;
    }

    public ExprlistComp2Group exprlistComp2Group() {
        return exprlistComp2Group;
    }

    // 'comp_for' | (',' 'expr_or_star')* [',']
    public static final class ExprlistComp2Group extends DisjunctionRule {
        private final CompFor compFor;
        private final ExprlistComp2Group2 exprlistComp2Group2;

        public ExprlistComp2Group(
                CompFor compFor,
                ExprlistComp2Group2 exprlistComp2Group2
        ) {
            this.compFor = compFor;
            this.exprlistComp2Group2 = exprlistComp2Group2;

            addChoice("compFor", compFor);
            addChoice("exprlistComp2Group2", exprlistComp2Group2);
        }

        public CompFor compFor() {
            return compFor;
        }

        public ExprlistComp2Group2 exprlistComp2Group2() {
            return exprlistComp2Group2;
        }
    }

    // (',' 'expr_or_star')* [',']
    public static final class ExprlistComp2Group2 extends ConjunctionRule {
        private final List<ExprlistComp2Group21Group> exprlistComp2Group21GroupList;
        private final boolean isTokenComma;

        public ExprlistComp2Group2(
                List<ExprlistComp2Group21Group> exprlistComp2Group21GroupList,
                boolean isTokenComma
        ) {
            this.exprlistComp2Group21GroupList = exprlistComp2Group21GroupList;
            this.isTokenComma = isTokenComma;

            addRequired("exprlistComp2Group21GroupList", exprlistComp2Group21GroupList);
            addRequired("isTokenComma", isTokenComma);
        }

        public List<ExprlistComp2Group21Group> exprlistComp2Group21GroupList() {
            return exprlistComp2Group21GroupList;
        }

        public boolean isTokenComma() {
            return isTokenComma;
        }
    }

    // ',' 'expr_or_star'
    public static final class ExprlistComp2Group21Group extends ConjunctionRule {
        private final boolean isTokenComma;
        private final ExprOrStar exprOrStar;

        public ExprlistComp2Group21Group(
                boolean isTokenComma,
                ExprOrStar exprOrStar
        ) {
            this.isTokenComma = isTokenComma;
            this.exprOrStar = exprOrStar;

            addRequired("isTokenComma", isTokenComma);
            addRequired("exprOrStar", exprOrStar);
        }

        public boolean isTokenComma() {
            return isTokenComma;
        }

        public ExprOrStar exprOrStar() {
            return exprOrStar;
        }
    }
}

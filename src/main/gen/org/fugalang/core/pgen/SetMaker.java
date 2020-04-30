package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;
import org.fugalang.core.parser.DisjunctionRule;

// set_maker: 'expr_or_star' ('comp_for' | (',' 'expr_or_star')* [','])
public final class SetMaker extends ConjunctionRule {
    private final ExprOrStar exprOrStar;
    private final SetMaker2Group setMaker2Group;

    public SetMaker(
            ExprOrStar exprOrStar,
            SetMaker2Group setMaker2Group
    ) {
        this.exprOrStar = exprOrStar;
        this.setMaker2Group = setMaker2Group;

        addRequired("exprOrStar", exprOrStar);
        addRequired("setMaker2Group", setMaker2Group);
    }

    public ExprOrStar exprOrStar() {
        return exprOrStar;
    }

    public SetMaker2Group setMaker2Group() {
        return setMaker2Group;
    }

    // 'comp_for' | (',' 'expr_or_star')* [',']
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

        public CompFor compFor() {
            return compFor;
        }

        public SetMaker2Group2 setMaker2Group2() {
            return setMaker2Group2;
        }
    }

    // (',' 'expr_or_star')* [',']
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

        public List<SetMaker2Group21Group> setMaker2Group21GroupList() {
            return setMaker2Group21GroupList;
        }

        public boolean isTokenComma() {
            return isTokenComma;
        }
    }

    // ',' 'expr_or_star'
    public static final class SetMaker2Group21Group extends ConjunctionRule {
        private final boolean isTokenComma;
        private final ExprOrStar exprOrStar;

        public SetMaker2Group21Group(
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

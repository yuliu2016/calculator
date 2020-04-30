package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;
import org.fugalang.core.parser.DisjunctionRule;

// set_maker: 'expr_or_star' ('comp_for' | (',' 'expr_or_star')* [','])
public final class SetMaker extends ConjunctionRule {
    private final ExprOrStar exprOrStar;
    private final SetMaker2 setMaker2;

    public SetMaker(
            ExprOrStar exprOrStar,
            SetMaker2 setMaker2
    ) {
        this.exprOrStar = exprOrStar;
        this.setMaker2 = setMaker2;

        addRequired("exprOrStar", exprOrStar);
        addRequired("setMaker2", setMaker2);
    }

    public ExprOrStar exprOrStar() {
        return exprOrStar;
    }

    public SetMaker2 setMaker2() {
        return setMaker2;
    }

    // 'comp_for' | (',' 'expr_or_star')* [',']
    public static final class SetMaker2 extends DisjunctionRule {
        private final CompFor compFor;
        private final SetMaker22 setMaker22;

        public SetMaker2(
                CompFor compFor,
                SetMaker22 setMaker22
        ) {
            this.compFor = compFor;
            this.setMaker22 = setMaker22;

            addChoice("compFor", compFor);
            addChoice("setMaker22", setMaker22);
        }

        public CompFor compFor() {
            return compFor;
        }

        public SetMaker22 setMaker22() {
            return setMaker22;
        }
    }

    // (',' 'expr_or_star')* [',']
    public static final class SetMaker22 extends ConjunctionRule {
        private final List<SetMaker221> setMaker221List;
        private final boolean isTokenComma;

        public SetMaker22(
                List<SetMaker221> setMaker221List,
                boolean isTokenComma
        ) {
            this.setMaker221List = setMaker221List;
            this.isTokenComma = isTokenComma;

            addRequired("setMaker221List", setMaker221List);
            addRequired("isTokenComma", isTokenComma);
        }

        public List<SetMaker221> setMaker221List() {
            return setMaker221List;
        }

        public boolean isTokenComma() {
            return isTokenComma;
        }
    }

    // ',' 'expr_or_star'
    public static final class SetMaker221 extends ConjunctionRule {
        private final boolean isTokenComma;
        private final ExprOrStar exprOrStar;

        public SetMaker221(
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

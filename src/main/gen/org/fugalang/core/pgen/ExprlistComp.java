package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;
import org.fugalang.core.parser.DisjunctionRule;

// exprlist_comp: 'expr_or_star' ('comp_for' | (',' 'expr_or_star')* [','])
public final class ExprlistComp extends ConjunctionRule {
    public static final String RULE_NAME = "exprlist_comp";

    private final ExprOrStar exprOrStar;
    private final ExprlistComp2 exprlistComp2;

    public ExprlistComp(
            ExprOrStar exprOrStar,
            ExprlistComp2 exprlistComp2
    ) {
        this.exprOrStar = exprOrStar;
        this.exprlistComp2 = exprlistComp2;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addRequired("exprOrStar", exprOrStar);
        addRequired("exprlistComp2", exprlistComp2);
    }

    public ExprOrStar exprOrStar() {
        return exprOrStar;
    }

    public ExprlistComp2 exprlistComp2() {
        return exprlistComp2;
    }

    // 'comp_for' | (',' 'expr_or_star')* [',']
    public static final class ExprlistComp2 extends DisjunctionRule {
        public static final String RULE_NAME = "exprlist_comp:2";

        private final CompFor compFor;
        private final ExprlistComp22 exprlistComp22;

        public ExprlistComp2(
                CompFor compFor,
                ExprlistComp22 exprlistComp22
        ) {
            this.compFor = compFor;
            this.exprlistComp22 = exprlistComp22;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addChoice("compFor", compFor);
            addChoice("exprlistComp22", exprlistComp22);
        }

        public CompFor compFor() {
            return compFor;
        }

        public ExprlistComp22 exprlistComp22() {
            return exprlistComp22;
        }
    }

    // (',' 'expr_or_star')* [',']
    public static final class ExprlistComp22 extends ConjunctionRule {
        public static final String RULE_NAME = "exprlist_comp:2:2";

        private final List<ExprlistComp221> exprlistComp221List;
        private final boolean isTokenComma;

        public ExprlistComp22(
                List<ExprlistComp221> exprlistComp221List,
                boolean isTokenComma
        ) {
            this.exprlistComp221List = exprlistComp221List;
            this.isTokenComma = isTokenComma;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("exprlistComp221List", exprlistComp221List);
            addRequired("isTokenComma", isTokenComma);
        }

        public List<ExprlistComp221> exprlistComp221List() {
            return exprlistComp221List;
        }

        public boolean isTokenComma() {
            return isTokenComma;
        }
    }

    // ',' 'expr_or_star'
    public static final class ExprlistComp221 extends ConjunctionRule {
        public static final String RULE_NAME = "exprlist_comp:2:2:1";

        private final boolean isTokenComma;
        private final ExprOrStar exprOrStar;

        public ExprlistComp221(
                boolean isTokenComma,
                ExprOrStar exprOrStar
        ) {
            this.isTokenComma = isTokenComma;
            this.exprOrStar = exprOrStar;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
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

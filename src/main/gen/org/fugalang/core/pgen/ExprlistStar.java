package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// exprlist_star: 'expr_or_star' (',' 'expr_or_star')* [',']
public final class ExprlistStar extends ConjunctionRule {
    private final ExprOrStar exprOrStar;
    private final List<ExprlistStar2> exprlistStar2List;
    private final boolean isTokenComma;

    public ExprlistStar(
            ExprOrStar exprOrStar,
            List<ExprlistStar2> exprlistStar2List,
            boolean isTokenComma
    ) {
        this.exprOrStar = exprOrStar;
        this.exprlistStar2List = exprlistStar2List;
        this.isTokenComma = isTokenComma;
    }

    @Override
    protected void buildRule() {
        addRequired("exprOrStar", exprOrStar);
        addRequired("exprlistStar2List", exprlistStar2List);
        addRequired("isTokenComma", isTokenComma);
    }

    public ExprOrStar exprOrStar() {
        return exprOrStar;
    }

    public List<ExprlistStar2> exprlistStar2List() {
        return exprlistStar2List;
    }

    public boolean isTokenComma() {
        return isTokenComma;
    }

    // ',' 'expr_or_star'
    public static final class ExprlistStar2 extends ConjunctionRule {
        private final boolean isTokenComma;
        private final ExprOrStar exprOrStar;

        public ExprlistStar2(
                boolean isTokenComma,
                ExprOrStar exprOrStar
        ) {
            this.isTokenComma = isTokenComma;
            this.exprOrStar = exprOrStar;
        }

        @Override
        protected void buildRule() {
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

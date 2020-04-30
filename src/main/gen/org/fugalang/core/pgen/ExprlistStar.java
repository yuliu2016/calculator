package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// exprlist_star: 'expr_or_star' (',' 'expr_or_star')* [',']
public final class ExprlistStar extends ConjunctionRule {
    private final ExprOrStar exprOrStar;
    private final List<ExprlistStar2Group> exprlistStar2GroupList;
    private final boolean isTokenComma;

    public ExprlistStar(
            ExprOrStar exprOrStar,
            List<ExprlistStar2Group> exprlistStar2GroupList,
            boolean isTokenComma
    ) {
        this.exprOrStar = exprOrStar;
        this.exprlistStar2GroupList = exprlistStar2GroupList;
        this.isTokenComma = isTokenComma;

        addRequired("exprOrStar", exprOrStar);
        addRequired("exprlistStar2GroupList", exprlistStar2GroupList);
        addRequired("isTokenComma", isTokenComma);
    }

    public ExprOrStar exprOrStar() {
        return exprOrStar;
    }

    public List<ExprlistStar2Group> exprlistStar2GroupList() {
        return exprlistStar2GroupList;
    }

    public boolean isTokenComma() {
        return isTokenComma;
    }

    // ',' 'expr_or_star'
    public static final class ExprlistStar2Group extends ConjunctionRule {
        private final boolean isTokenComma;
        private final ExprOrStar exprOrStar;

        public ExprlistStar2Group(
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

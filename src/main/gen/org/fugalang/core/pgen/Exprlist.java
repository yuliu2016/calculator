package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// exprlist: 'expr' (',' 'expr')* [',']
public final class Exprlist extends ConjunctionRule {
    private final Expr expr;
    private final List<Exprlist2> exprlist2List;
    private final boolean isTokenComma;

    public Exprlist(
            Expr expr,
            List<Exprlist2> exprlist2List,
            boolean isTokenComma
    ) {
        this.expr = expr;
        this.exprlist2List = exprlist2List;
        this.isTokenComma = isTokenComma;
    }

    @Override
    protected void buildRule() {
        addRequired("expr", expr);
        addRequired("exprlist2List", exprlist2List);
        addRequired("isTokenComma", isTokenComma);
    }

    public Expr expr() {
        return expr;
    }

    public List<Exprlist2> exprlist2List() {
        return exprlist2List;
    }

    public boolean isTokenComma() {
        return isTokenComma;
    }

    // ',' 'expr'
    public static final class Exprlist2 extends ConjunctionRule {
        private final boolean isTokenComma;
        private final Expr expr;

        public Exprlist2(
                boolean isTokenComma,
                Expr expr
        ) {
            this.isTokenComma = isTokenComma;
            this.expr = expr;
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenComma", isTokenComma);
            addRequired("expr", expr);
        }

        public boolean isTokenComma() {
            return isTokenComma;
        }

        public Expr expr() {
            return expr;
        }
    }
}

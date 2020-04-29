package org.fugalang.core.pgen;

import java.util.List;

// exprlist: 'expr' (',' 'expr')* [',']
public class Exprlist {
    private final Expr expr;
    private final List<Exprlist2Group> exprlist2GroupList;
    private final boolean isTokenComma;

    public Exprlist(
            Expr expr,
            List<Exprlist2Group> exprlist2GroupList,
            boolean isTokenComma
    ) {
        this.expr = expr;
        this.exprlist2GroupList = exprlist2GroupList;
        this.isTokenComma = isTokenComma;
    }

    public Expr getExpr() {
        return expr;
    }

    public List<Exprlist2Group> getExprlist2GroupList() {
        return exprlist2GroupList;
    }

    public boolean getIsTokenComma() {
        return isTokenComma;
    }

    // ',' 'expr'
    public static class Exprlist2Group {
        private final boolean isTokenComma;
        private final Expr expr;

        public Exprlist2Group(
                boolean isTokenComma,
                Expr expr
        ) {
            this.isTokenComma = isTokenComma;
            this.expr = expr;
        }

        public boolean getIsTokenComma() {
            return isTokenComma;
        }

        public Expr getExpr() {
            return expr;
        }
    }
}

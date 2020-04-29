package org.fugalang.core.pgen;

import java.util.List;

// exprlist: 'expr' (',' 'expr')* [',']
public class Exprlist {
    public final Expr expr;
    public final List<Exprlist2Group> exprlist2GroupList;
    public final boolean isTokenComma;

    public Exprlist(
            Expr expr,
            List<Exprlist2Group> exprlist2GroupList,
            boolean isTokenComma
    ) {
        this.expr = expr;
        this.exprlist2GroupList = exprlist2GroupList;
        this.isTokenComma = isTokenComma;
    }

    // ',' 'expr'
    public static class Exprlist2Group {
        public final boolean isTokenComma;
        public final Expr expr;

        public Exprlist2Group(
                boolean isTokenComma,
                Expr expr
        ) {
            this.isTokenComma = isTokenComma;
            this.expr = expr;
        }
    }
}

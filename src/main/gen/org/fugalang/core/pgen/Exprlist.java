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
}

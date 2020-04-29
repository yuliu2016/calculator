package org.fugalang.core.pgen;

// exprlist: 'expr' (',' 'expr')* [',']
public class Exprlist {
    public final Expr expr;
    public final Exprlist2Group exprlist2Group;
    public final boolean isTokenComma;

    public Exprlist(
            Expr expr,
            Exprlist2Group exprlist2Group,
            boolean isTokenComma
    ) {
        this.expr = expr;
        this.exprlist2Group = exprlist2Group;
        this.isTokenComma = isTokenComma;
    }
}

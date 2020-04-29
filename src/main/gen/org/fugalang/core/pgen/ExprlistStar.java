package org.fugalang.core.pgen;

// exprlist_star: ('expr' | 'star_expr') (',' ('expr' | 'star_expr'))* [',']
public class ExprlistStar {
    public final ExprlistStar1Group exprlistStar1Group;
    public final ExprlistStar2Group exprlistStar2Group;
    public final boolean isTokenComma;

    public ExprlistStar(
            ExprlistStar1Group exprlistStar1Group,
            ExprlistStar2Group exprlistStar2Group,
            boolean isTokenComma
    ) {
        this.exprlistStar1Group = exprlistStar1Group;
        this.exprlistStar2Group = exprlistStar2Group;
        this.isTokenComma = isTokenComma;
    }
}

package org.fugalang.core.pgen;

// ',' ('expr' | 'star_expr')
public class ExprlistStar2Group {
    public final boolean isTokenComma;
    public final ExprlistStar2Group2Group exprlistStar2Group2Group;

    public ExprlistStar2Group(
            boolean isTokenComma,
            ExprlistStar2Group2Group exprlistStar2Group2Group
    ) {
        this.isTokenComma = isTokenComma;
        this.exprlistStar2Group2Group = exprlistStar2Group2Group;
    }
}

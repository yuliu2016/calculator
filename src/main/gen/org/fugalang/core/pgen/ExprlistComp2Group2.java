package org.fugalang.core.pgen;

// (',' ('namedexpr_expr' | 'star_expr'))* [',']
public class ExprlistComp2Group2 {
    public final ExprlistComp2Group21Group exprlistComp2Group21Group;
    public final boolean isTokenComma;

    public ExprlistComp2Group2(
            ExprlistComp2Group21Group exprlistComp2Group21Group,
            boolean isTokenComma
    ) {
        this.exprlistComp2Group21Group = exprlistComp2Group21Group;
        this.isTokenComma = isTokenComma;
    }
}

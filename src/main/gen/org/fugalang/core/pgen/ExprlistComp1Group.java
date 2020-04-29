package org.fugalang.core.pgen;

// 'namedexpr_expr' | 'star_expr'
public class ExprlistComp1Group {
    public final NamedexprExpr namedexprExpr;
    public final StarExpr starExpr;

    public ExprlistComp1Group(
            NamedexprExpr namedexprExpr,
            StarExpr starExpr
    ) {
        this.namedexprExpr = namedexprExpr;
        this.starExpr = starExpr;
    }
}

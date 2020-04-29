package org.fugalang.core.pgen;

// 'comp_for' | (',' ('namedexpr_expr' | 'star_expr'))* [',']
public class ExprlistComp2Group {
    public final CompFor compFor;
    public final ExprlistComp2Group2 exprlistComp2Group2;

    public ExprlistComp2Group(
            CompFor compFor,
            ExprlistComp2Group2 exprlistComp2Group2
    ) {
        this.compFor = compFor;
        this.exprlistComp2Group2 = exprlistComp2Group2;
    }
}

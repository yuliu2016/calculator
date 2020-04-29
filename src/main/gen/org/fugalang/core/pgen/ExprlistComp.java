package org.fugalang.core.pgen;

// exprlist_comp: ('namedexpr_expr' | 'star_expr') ('comp_for' | (',' ('namedexpr_expr' | 'star_expr'))* [','])
public class ExprlistComp {
    public final ExprlistComp1Group exprlistComp1Group;
    public final ExprlistComp2Group exprlistComp2Group;

    public ExprlistComp(
            ExprlistComp1Group exprlistComp1Group,
            ExprlistComp2Group exprlistComp2Group
    ) {
        this.exprlistComp1Group = exprlistComp1Group;
        this.exprlistComp2Group = exprlistComp2Group;
    }
}

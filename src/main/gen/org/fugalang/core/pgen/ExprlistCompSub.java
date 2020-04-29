package org.fugalang.core.pgen;

// exprlist_comp_sub: 'exprlist_comp' | 'subscript'
public class ExprlistCompSub {
    public final ExprlistComp exprlistComp;
    public final Subscript subscript;

    public ExprlistCompSub(
            ExprlistComp exprlistComp,
            Subscript subscript
    ) {
        this.exprlistComp = exprlistComp;
        this.subscript = subscript;
    }
}

package org.fugalang.core.pgen;

// exprlist_comp_sub: 'exprlist_comp' | 'subscript'
public class ExprlistCompSub {
    private final ExprlistComp exprlistComp;
    private final Subscript subscript;

    public ExprlistCompSub(
            ExprlistComp exprlistComp,
            Subscript subscript
    ) {
        this.exprlistComp = exprlistComp;
        this.subscript = subscript;
    }

    public ExprlistComp getExprlistComp() {
        return exprlistComp;
    }

    public Subscript getSubscript() {
        return subscript;
    }
}

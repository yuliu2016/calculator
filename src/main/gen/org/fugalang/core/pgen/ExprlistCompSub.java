package org.fugalang.core.pgen;

import org.fugalang.core.parser.DisjunctionRule;

// exprlist_comp_sub: 'exprlist_comp' | 'subscript'
public final class ExprlistCompSub extends DisjunctionRule {
    private final ExprlistComp exprlistComp;
    private final Subscript subscript;

    public ExprlistCompSub(
            ExprlistComp exprlistComp,
            Subscript subscript
    ) {
        this.exprlistComp = exprlistComp;
        this.subscript = subscript;
    }

    @Override
    protected void buildRule() {
        addChoice("exprlistComp", exprlistComp);
        addChoice("subscript", subscript);
    }

    public ExprlistComp exprlistComp() {
        return exprlistComp;
    }

    public Subscript subscript() {
        return subscript;
    }
}

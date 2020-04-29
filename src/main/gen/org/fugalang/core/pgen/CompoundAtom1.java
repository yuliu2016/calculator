package org.fugalang.core.pgen;

// '(' ['exprlist_comp'] ')'
public class CompoundAtom1 {
    public final boolean isTokenLpar;
    public final ExprlistComp exprlistComp;
    public final boolean isTokenRpar;

    public CompoundAtom1(
            boolean isTokenLpar,
            ExprlistComp exprlistComp,
            boolean isTokenRpar
    ) {
        this.isTokenLpar = isTokenLpar;
        this.exprlistComp = exprlistComp;
        this.isTokenRpar = isTokenRpar;
    }
}

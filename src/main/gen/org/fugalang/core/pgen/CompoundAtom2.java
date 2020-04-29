package org.fugalang.core.pgen;

// '[' ['exprlist_comp_sub'] ']'
public class CompoundAtom2 {
    public final boolean isTokenLsqb;
    public final ExprlistCompSub exprlistCompSub;
    public final boolean isTokenRsqb;

    public CompoundAtom2(
            boolean isTokenLsqb,
            ExprlistCompSub exprlistCompSub,
            boolean isTokenRsqb
    ) {
        this.isTokenLsqb = isTokenLsqb;
        this.exprlistCompSub = exprlistCompSub;
        this.isTokenRsqb = isTokenRsqb;
    }
}

package org.fugalang.core.pgen;

// compound_atom: '(' ['exprlist_comp'] ')' | '[' ['exprlist_comp_sub'] ']' | '{' ['dictorsetmaker'] '}'
public class CompoundAtom {
    public final CompoundAtom1 compoundAtom1;
    public final CompoundAtom2 compoundAtom2;
    public final CompoundAtom3 compoundAtom3;

    public CompoundAtom(
            CompoundAtom1 compoundAtom1,
            CompoundAtom2 compoundAtom2,
            CompoundAtom3 compoundAtom3
    ) {
        this.compoundAtom1 = compoundAtom1;
        this.compoundAtom2 = compoundAtom2;
        this.compoundAtom3 = compoundAtom3;
    }

    // '(' ['exprlist_comp'] ')'
    public static class CompoundAtom1 {
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

    // '[' ['exprlist_comp_sub'] ']'
    public static class CompoundAtom2 {
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

    // '{' ['dictorsetmaker'] '}'
    public static class CompoundAtom3 {
        public final boolean isTokenLbrace;
        public final Dictorsetmaker dictorsetmaker;
        public final boolean isTokenRbrace;

        public CompoundAtom3(
                boolean isTokenLbrace,
                Dictorsetmaker dictorsetmaker,
                boolean isTokenRbrace
        ) {
            this.isTokenLbrace = isTokenLbrace;
            this.dictorsetmaker = dictorsetmaker;
            this.isTokenRbrace = isTokenRbrace;
        }
    }
}

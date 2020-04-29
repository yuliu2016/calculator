package org.fugalang.core.pgen;

// compound_atom: '(' ['exprlist_comp'] ')' | '[' ['exprlist_comp_sub'] ']' | '{' ['dictorsetmaker'] '}'
public class CompoundAtom {
    private final CompoundAtom1 compoundAtom1;
    private final CompoundAtom2 compoundAtom2;
    private final CompoundAtom3 compoundAtom3;

    public CompoundAtom(
            CompoundAtom1 compoundAtom1,
            CompoundAtom2 compoundAtom2,
            CompoundAtom3 compoundAtom3
    ) {
        this.compoundAtom1 = compoundAtom1;
        this.compoundAtom2 = compoundAtom2;
        this.compoundAtom3 = compoundAtom3;
    }

    public CompoundAtom1 getCompoundAtom1() {
        return compoundAtom1;
    }

    public CompoundAtom2 getCompoundAtom2() {
        return compoundAtom2;
    }

    public CompoundAtom3 getCompoundAtom3() {
        return compoundAtom3;
    }

    // '(' ['exprlist_comp'] ')'
    public static class CompoundAtom1 {
        private final boolean isTokenLpar;
        private final ExprlistComp exprlistComp;
        private final boolean isTokenRpar;

        public CompoundAtom1(
                boolean isTokenLpar,
                ExprlistComp exprlistComp,
                boolean isTokenRpar
        ) {
            this.isTokenLpar = isTokenLpar;
            this.exprlistComp = exprlistComp;
            this.isTokenRpar = isTokenRpar;
        }

        public boolean getIsTokenLpar() {
            return isTokenLpar;
        }

        public ExprlistComp getExprlistComp() {
            return exprlistComp;
        }

        public boolean getIsTokenRpar() {
            return isTokenRpar;
        }
    }

    // '[' ['exprlist_comp_sub'] ']'
    public static class CompoundAtom2 {
        private final boolean isTokenLsqb;
        private final ExprlistCompSub exprlistCompSub;
        private final boolean isTokenRsqb;

        public CompoundAtom2(
                boolean isTokenLsqb,
                ExprlistCompSub exprlistCompSub,
                boolean isTokenRsqb
        ) {
            this.isTokenLsqb = isTokenLsqb;
            this.exprlistCompSub = exprlistCompSub;
            this.isTokenRsqb = isTokenRsqb;
        }

        public boolean getIsTokenLsqb() {
            return isTokenLsqb;
        }

        public ExprlistCompSub getExprlistCompSub() {
            return exprlistCompSub;
        }

        public boolean getIsTokenRsqb() {
            return isTokenRsqb;
        }
    }

    // '{' ['dictorsetmaker'] '}'
    public static class CompoundAtom3 {
        private final boolean isTokenLbrace;
        private final Dictorsetmaker dictorsetmaker;
        private final boolean isTokenRbrace;

        public CompoundAtom3(
                boolean isTokenLbrace,
                Dictorsetmaker dictorsetmaker,
                boolean isTokenRbrace
        ) {
            this.isTokenLbrace = isTokenLbrace;
            this.dictorsetmaker = dictorsetmaker;
            this.isTokenRbrace = isTokenRbrace;
        }

        public boolean getIsTokenLbrace() {
            return isTokenLbrace;
        }

        public Dictorsetmaker getDictorsetmaker() {
            return dictorsetmaker;
        }

        public boolean getIsTokenRbrace() {
            return isTokenRbrace;
        }
    }
}

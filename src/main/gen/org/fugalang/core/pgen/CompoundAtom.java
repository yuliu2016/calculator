package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.Optional;
import org.fugalang.core.parser.DisjunctionRule;

// compound_atom: '(' ['exprlist_comp'] ')' | '[' ['exprlist_comp_sub'] ']' | '{' ['dictorsetmaker'] '}'
public final class CompoundAtom extends DisjunctionRule {
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

    @Override
    protected void buildRule() {
        addChoice("compoundAtom1", compoundAtom1);
        addChoice("compoundAtom2", compoundAtom2);
        addChoice("compoundAtom3", compoundAtom3);
    }

    public CompoundAtom1 compoundAtom1() {
        return compoundAtom1;
    }

    public CompoundAtom2 compoundAtom2() {
        return compoundAtom2;
    }

    public CompoundAtom3 compoundAtom3() {
        return compoundAtom3;
    }

    // '(' ['exprlist_comp'] ')'
    public static final class CompoundAtom1 extends ConjunctionRule {
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

        @Override
        protected void buildRule() {
            addRequired("isTokenLpar", isTokenLpar);
            addOptional("exprlistComp", exprlistComp);
            addRequired("isTokenRpar", isTokenRpar);
        }

        public boolean isTokenLpar() {
            return isTokenLpar;
        }

        public Optional<ExprlistComp> exprlistComp() {
            return Optional.ofNullable(exprlistComp);
        }

        public boolean isTokenRpar() {
            return isTokenRpar;
        }
    }

    // '[' ['exprlist_comp_sub'] ']'
    public static final class CompoundAtom2 extends ConjunctionRule {
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

        @Override
        protected void buildRule() {
            addRequired("isTokenLsqb", isTokenLsqb);
            addOptional("exprlistCompSub", exprlistCompSub);
            addRequired("isTokenRsqb", isTokenRsqb);
        }

        public boolean isTokenLsqb() {
            return isTokenLsqb;
        }

        public Optional<ExprlistCompSub> exprlistCompSub() {
            return Optional.ofNullable(exprlistCompSub);
        }

        public boolean isTokenRsqb() {
            return isTokenRsqb;
        }
    }

    // '{' ['dictorsetmaker'] '}'
    public static final class CompoundAtom3 extends ConjunctionRule {
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

        @Override
        protected void buildRule() {
            addRequired("isTokenLbrace", isTokenLbrace);
            addOptional("dictorsetmaker", dictorsetmaker);
            addRequired("isTokenRbrace", isTokenRbrace);
        }

        public boolean isTokenLbrace() {
            return isTokenLbrace;
        }

        public Optional<Dictorsetmaker> dictorsetmaker() {
            return Optional.ofNullable(dictorsetmaker);
        }

        public boolean isTokenRbrace() {
            return isTokenRbrace;
        }
    }
}

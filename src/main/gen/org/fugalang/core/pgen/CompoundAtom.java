package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * compound_atom: '(' ['exprlist_comp'] ')' | '[' ['exprlist_comp_sub'] ']' | '{' ['dictorsetmaker'] '}'
 */
public final class CompoundAtom extends DisjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("compound_atom", RuleType.Disjunction, true);

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
        addChoice("compoundAtom1", compoundAtom1());
        addChoice("compoundAtom2", compoundAtom2());
        addChoice("compoundAtom3", compoundAtom3());
    }

    public CompoundAtom1 compoundAtom1() {
        return compoundAtom1;
    }

    public boolean hasCompoundAtom1() {
        return compoundAtom1() != null;
    }

    public CompoundAtom2 compoundAtom2() {
        return compoundAtom2;
    }

    public boolean hasCompoundAtom2() {
        return compoundAtom2() != null;
    }

    public CompoundAtom3 compoundAtom3() {
        return compoundAtom3;
    }

    public boolean hasCompoundAtom3() {
        return compoundAtom3() != null;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = CompoundAtom1.parse(parseTree, level + 1);
        result = result || CompoundAtom2.parse(parseTree, level + 1);
        result = result || CompoundAtom3.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * '(' ['exprlist_comp'] ')'
     */
    public static final class CompoundAtom1 extends ConjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("compound_atom:1", RuleType.Conjunction, false);

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
            addRequired("isTokenLpar", isTokenLpar());
            addOptional("exprlistComp", exprlistComp());
            addRequired("isTokenRpar", isTokenRpar());
        }

        public boolean isTokenLpar() {
            return isTokenLpar;
        }

        public ExprlistComp exprlistComp() {
            return exprlistComp;
        }

        public boolean hasExprlistComp() {
            return exprlistComp() != null;
        }

        public boolean isTokenRpar() {
            return isTokenRpar;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral("(");
            ExprlistComp.parse(parseTree, level + 1);
            result = result && parseTree.consumeTokenLiteral(")");

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * '[' ['exprlist_comp_sub'] ']'
     */
    public static final class CompoundAtom2 extends ConjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("compound_atom:2", RuleType.Conjunction, false);

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
            addRequired("isTokenLsqb", isTokenLsqb());
            addOptional("exprlistCompSub", exprlistCompSub());
            addRequired("isTokenRsqb", isTokenRsqb());
        }

        public boolean isTokenLsqb() {
            return isTokenLsqb;
        }

        public ExprlistCompSub exprlistCompSub() {
            return exprlistCompSub;
        }

        public boolean hasExprlistCompSub() {
            return exprlistCompSub() != null;
        }

        public boolean isTokenRsqb() {
            return isTokenRsqb;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral("[");
            ExprlistCompSub.parse(parseTree, level + 1);
            result = result && parseTree.consumeTokenLiteral("]");

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * '{' ['dictorsetmaker'] '}'
     */
    public static final class CompoundAtom3 extends ConjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("compound_atom:3", RuleType.Conjunction, false);

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
            addRequired("isTokenLbrace", isTokenLbrace());
            addOptional("dictorsetmaker", dictorsetmaker());
            addRequired("isTokenRbrace", isTokenRbrace());
        }

        public boolean isTokenLbrace() {
            return isTokenLbrace;
        }

        public Dictorsetmaker dictorsetmaker() {
            return dictorsetmaker;
        }

        public boolean hasDictorsetmaker() {
            return dictorsetmaker() != null;
        }

        public boolean isTokenRbrace() {
            return isTokenRbrace;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral("{");
            Dictorsetmaker.parse(parseTree, level + 1);
            result = result && parseTree.consumeTokenLiteral("}");

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}

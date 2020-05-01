package org.fugalang.core.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.ConjunctionRule;
import java.util.Optional;
import org.fugalang.core.parser.DisjunctionRule;

// compound_atom: '(' ['exprlist_comp'] ')' | '[' ['exprlist_comp_sub'] ']' | '{' ['dictorsetmaker'] '}'
public final class CompoundAtom extends DisjunctionRule {
    public static final String RULE_NAME = "compound_atom";

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
        setExplicitName(RULE_NAME);
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

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        boolean result;

        result = CompoundAtom1.parse(parseTree, level + 1);
        if (!result) result = CompoundAtom2.parse(parseTree, level + 1);
        if (!result) result = CompoundAtom3.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    // '(' ['exprlist_comp'] ')'
    public static final class CompoundAtom1 extends ConjunctionRule {
        public static final String RULE_NAME = "compound_atom:1";

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
            setImpliedName(RULE_NAME);
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

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            result = parseTree.consumeTokenLiteral("(");
            ExprlistComp.parse(parseTree, level + 1);
            result = result && parseTree.consumeTokenLiteral(")");

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    // '[' ['exprlist_comp_sub'] ']'
    public static final class CompoundAtom2 extends ConjunctionRule {
        public static final String RULE_NAME = "compound_atom:2";

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
            setImpliedName(RULE_NAME);
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

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            result = parseTree.consumeTokenLiteral("[");
            ExprlistCompSub.parse(parseTree, level + 1);
            result = result && parseTree.consumeTokenLiteral("]");

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    // '{' ['dictorsetmaker'] '}'
    public static final class CompoundAtom3 extends ConjunctionRule {
        public static final String RULE_NAME = "compound_atom:3";

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
            setImpliedName(RULE_NAME);
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

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            result = parseTree.consumeTokenLiteral("{");
            Dictorsetmaker.parse(parseTree, level + 1);
            result = result && parseTree.consumeTokenLiteral("}");

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}

package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * compound_atom: '(' ['exprlist_comp'] ')' | '[' ['exprlist_comp_sub'] ']' | '{' ['dictorsetmaker'] '}'
 */
public final class CompoundAtom extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("compound_atom", RuleType.Disjunction, true);

    public static CompoundAtom of(ParseTreeNode node) {
        return new CompoundAtom(node);
    }

    private CompoundAtom(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addChoice("compoundAtom1", compoundAtom1());
        addChoice("compoundAtom2", compoundAtom2());
        addChoice("compoundAtom3", compoundAtom3());
    }

    public CompoundAtom1 compoundAtom1() {
        var element = getItem(0);
        if (!element.isPresent()) return null;
        return CompoundAtom1.of(element);
    }

    public boolean hasCompoundAtom1() {
        return compoundAtom1() != null;
    }

    public CompoundAtom2 compoundAtom2() {
        var element = getItem(1);
        if (!element.isPresent()) return null;
        return CompoundAtom2.of(element);
    }

    public boolean hasCompoundAtom2() {
        return compoundAtom2() != null;
    }

    public CompoundAtom3 compoundAtom3() {
        var element = getItem(2);
        if (!element.isPresent()) return null;
        return CompoundAtom3.of(element);
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
    public static final class CompoundAtom1 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("compound_atom:1", RuleType.Conjunction, false);

        public static CompoundAtom1 of(ParseTreeNode node) {
            return new CompoundAtom1(node);
        }

        private CompoundAtom1(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenLpar", isTokenLpar());
            addOptional("exprlistComp", exprlistComp());
            addRequired("isTokenRpar", isTokenRpar());
        }

        public boolean isTokenLpar() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public ExprlistComp exprlistComp() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return ExprlistComp.of(element);
        }

        public boolean hasExprlistComp() {
            return exprlistComp() != null;
        }

        public boolean isTokenRpar() {
            var element = getItem(2);
            return element.asBoolean();
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
    public static final class CompoundAtom2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("compound_atom:2", RuleType.Conjunction, false);

        public static CompoundAtom2 of(ParseTreeNode node) {
            return new CompoundAtom2(node);
        }

        private CompoundAtom2(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenLsqb", isTokenLsqb());
            addOptional("exprlistCompSub", exprlistCompSub());
            addRequired("isTokenRsqb", isTokenRsqb());
        }

        public boolean isTokenLsqb() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public ExprlistCompSub exprlistCompSub() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return ExprlistCompSub.of(element);
        }

        public boolean hasExprlistCompSub() {
            return exprlistCompSub() != null;
        }

        public boolean isTokenRsqb() {
            var element = getItem(2);
            return element.asBoolean();
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
    public static final class CompoundAtom3 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("compound_atom:3", RuleType.Conjunction, false);

        public static CompoundAtom3 of(ParseTreeNode node) {
            return new CompoundAtom3(node);
        }

        private CompoundAtom3(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenLbrace", isTokenLbrace());
            addOptional("dictorsetmaker", dictorsetmaker());
            addRequired("isTokenRbrace", isTokenRbrace());
        }

        public boolean isTokenLbrace() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public Dictorsetmaker dictorsetmaker() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return Dictorsetmaker.of(element);
        }

        public boolean hasDictorsetmaker() {
            return dictorsetmaker() != null;
        }

        public boolean isTokenRbrace() {
            var element = getItem(2);
            return element.asBoolean();
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

package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * compound_atom: '(' ['exprlist_comp'] ')' | '[' ['exprlist_comp'] ']' | '{' ['dictorsetmaker'] '}'
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
        addChoice(compoundAtom1OrNull());
        addChoice(compoundAtom2OrNull());
        addChoice(compoundAtom3OrNull());
    }

    public CompoundAtom1 compoundAtom1() {
        var element = getItem(0);
        element.failIfAbsent(CompoundAtom1.RULE);
        return CompoundAtom1.of(element);
    }

    public CompoundAtom1 compoundAtom1OrNull() {
        var element = getItem(0);
        if (!element.isPresent(CompoundAtom1.RULE)) {
            return null;
        }
        return CompoundAtom1.of(element);
    }

    public boolean hasCompoundAtom1() {
        var element = getItem(0);
        return element.isPresent(CompoundAtom1.RULE);
    }

    public CompoundAtom2 compoundAtom2() {
        var element = getItem(1);
        element.failIfAbsent(CompoundAtom2.RULE);
        return CompoundAtom2.of(element);
    }

    public CompoundAtom2 compoundAtom2OrNull() {
        var element = getItem(1);
        if (!element.isPresent(CompoundAtom2.RULE)) {
            return null;
        }
        return CompoundAtom2.of(element);
    }

    public boolean hasCompoundAtom2() {
        var element = getItem(1);
        return element.isPresent(CompoundAtom2.RULE);
    }

    public CompoundAtom3 compoundAtom3() {
        var element = getItem(2);
        element.failIfAbsent(CompoundAtom3.RULE);
        return CompoundAtom3.of(element);
    }

    public CompoundAtom3 compoundAtom3OrNull() {
        var element = getItem(2);
        if (!element.isPresent(CompoundAtom3.RULE)) {
            return null;
        }
        return CompoundAtom3.of(element);
    }

    public boolean hasCompoundAtom3() {
        var element = getItem(2);
        return element.isPresent(CompoundAtom3.RULE);
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
            addRequired(isTokenLpar(), "(");
            addOptional(exprlistCompOrNull());
            addRequired(isTokenRpar(), ")");
        }

        public boolean isTokenLpar() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public ExprlistComp exprlistComp() {
            var element = getItem(1);
            element.failIfAbsent(ExprlistComp.RULE);
            return ExprlistComp.of(element);
        }

        public ExprlistComp exprlistCompOrNull() {
            var element = getItem(1);
            if (!element.isPresent(ExprlistComp.RULE)) {
                return null;
            }
            return ExprlistComp.of(element);
        }

        public boolean hasExprlistComp() {
            var element = getItem(1);
            return element.isPresent(ExprlistComp.RULE);
        }

        public boolean isTokenRpar() {
            var element = getItem(2);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("(");
            if (result) ExprlistComp.parse(parseTree, level + 1);
            result = result && parseTree.consumeToken(")");

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * '[' ['exprlist_comp'] ']'
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
            addRequired(isTokenLsqb(), "[");
            addOptional(exprlistCompOrNull());
            addRequired(isTokenRsqb(), "]");
        }

        public boolean isTokenLsqb() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public ExprlistComp exprlistComp() {
            var element = getItem(1);
            element.failIfAbsent(ExprlistComp.RULE);
            return ExprlistComp.of(element);
        }

        public ExprlistComp exprlistCompOrNull() {
            var element = getItem(1);
            if (!element.isPresent(ExprlistComp.RULE)) {
                return null;
            }
            return ExprlistComp.of(element);
        }

        public boolean hasExprlistComp() {
            var element = getItem(1);
            return element.isPresent(ExprlistComp.RULE);
        }

        public boolean isTokenRsqb() {
            var element = getItem(2);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("[");
            if (result) ExprlistComp.parse(parseTree, level + 1);
            result = result && parseTree.consumeToken("]");

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
            addRequired(isTokenLbrace(), "{");
            addOptional(dictorsetmakerOrNull());
            addRequired(isTokenRbrace(), "}");
        }

        public boolean isTokenLbrace() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public Dictorsetmaker dictorsetmaker() {
            var element = getItem(1);
            element.failIfAbsent(Dictorsetmaker.RULE);
            return Dictorsetmaker.of(element);
        }

        public Dictorsetmaker dictorsetmakerOrNull() {
            var element = getItem(1);
            if (!element.isPresent(Dictorsetmaker.RULE)) {
                return null;
            }
            return Dictorsetmaker.of(element);
        }

        public boolean hasDictorsetmaker() {
            var element = getItem(1);
            return element.isPresent(Dictorsetmaker.RULE);
        }

        public boolean isTokenRbrace() {
            var element = getItem(2);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("{");
            if (result) Dictorsetmaker.parse(parseTree, level + 1);
            result = result && parseTree.consumeToken("}");

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}

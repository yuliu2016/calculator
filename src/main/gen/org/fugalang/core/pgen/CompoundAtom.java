package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * compound_atom: '(' ['named_expr_list'] ')' | '[' ['named_expr_list'] ']' | '{' ['dict_or_set'] '}'
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

    public CompoundAtom1 compoundAtom1() {
        return CompoundAtom1.of(getItem(0));
    }

    public boolean hasCompoundAtom1() {
        return hasItemOfRule(0, CompoundAtom1.RULE);
    }

    public CompoundAtom2 compoundAtom2() {
        return CompoundAtom2.of(getItem(1));
    }

    public boolean hasCompoundAtom2() {
        return hasItemOfRule(1, CompoundAtom2.RULE);
    }

    public CompoundAtom3 compoundAtom3() {
        return CompoundAtom3.of(getItem(2));
    }

    public boolean hasCompoundAtom3() {
        return hasItemOfRule(2, CompoundAtom3.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = CompoundAtom1.parse(parseTree, level + 1);
        result = result || CompoundAtom2.parse(parseTree, level + 1);
        result = result || CompoundAtom3.parse(parseTree, level + 1);

        parseTree.exit(result);
        return result;
    }

    /**
     * '(' ['named_expr_list'] ')'
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

        public NamedExprList namedExprList() {
            return NamedExprList.of(getItem(1));
        }

        public boolean hasNamedExprList() {
            return hasItemOfRule(1, NamedExprList.RULE);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("(");
            if (result) NamedExprList.parse(parseTree, level + 1);
            result = result && parseTree.consumeToken(")");

            parseTree.exit(result);
            return result;
        }
    }

    /**
     * '[' ['named_expr_list'] ']'
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

        public NamedExprList namedExprList() {
            return NamedExprList.of(getItem(1));
        }

        public boolean hasNamedExprList() {
            return hasItemOfRule(1, NamedExprList.RULE);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("[");
            if (result) NamedExprList.parse(parseTree, level + 1);
            result = result && parseTree.consumeToken("]");

            parseTree.exit(result);
            return result;
        }
    }

    /**
     * '{' ['dict_or_set'] '}'
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

        public DictOrSet dictOrSet() {
            return DictOrSet.of(getItem(1));
        }

        public boolean hasDictOrSet() {
            return hasItemOfRule(1, DictOrSet.RULE);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("{");
            if (result) DictOrSet.parse(parseTree, level + 1);
            result = result && parseTree.consumeToken("}");

            parseTree.exit(result);
            return result;
        }
    }
}

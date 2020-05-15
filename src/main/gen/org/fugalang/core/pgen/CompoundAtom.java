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

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = CompoundAtom1.parse(t, l + 1);
        r = r || CompoundAtom2.parse(t, l + 1);
        r = r || CompoundAtom3.parse(t, l + 1);
        t.exit(r);
        return r;
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

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = t.consumeToken("(");
            if (r) NamedExprList.parse(t, l + 1);
            r = r && t.consumeToken(")");
            t.exit(r);
            return r;
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

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = t.consumeToken("[");
            if (r) NamedExprList.parse(t, l + 1);
            r = r && t.consumeToken("]");
            t.exit(r);
            return r;
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

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = t.consumeToken("{");
            if (r) DictOrSet.parse(t, l + 1);
            r = r && t.consumeToken("}");
            t.exit(r);
            return r;
        }
    }
}

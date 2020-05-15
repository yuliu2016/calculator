package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * slice: 'expr' | ['expr'] ':' ['expr'] [':' ['expr']]
 */
public final class Slice extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("slice", RuleType.Disjunction, true);

    public static Slice of(ParseTreeNode node) {
        return new Slice(node);
    }

    private Slice(ParseTreeNode node) {
        super(RULE, node);
    }

    public Expr expr() {
        return Expr.of(getItem(0));
    }

    public boolean hasExpr() {
        return hasItemOfRule(0, Expr.RULE);
    }

    public Slice2 slice2() {
        return Slice2.of(getItem(1));
    }

    public boolean hasSlice2() {
        return hasItemOfRule(1, Slice2.RULE);
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = Expr.parse(t, l + 1);
        r = r || Slice2.parse(t, l + 1);
        t.exit(r);
        return r;
    }

    /**
     * ['expr'] ':' ['expr'] [':' ['expr']]
     */
    public static final class Slice2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("slice:2", RuleType.Conjunction, false);

        public static Slice2 of(ParseTreeNode node) {
            return new Slice2(node);
        }

        private Slice2(ParseTreeNode node) {
            super(RULE, node);
        }

        public Expr expr() {
            return Expr.of(getItem(0));
        }

        public boolean hasExpr() {
            return hasItemOfRule(0, Expr.RULE);
        }

        public Expr expr1() {
            return Expr.of(getItem(2));
        }

        public boolean hasExpr1() {
            return hasItemOfRule(2, Expr.RULE);
        }

        public Slice24 slice24() {
            return Slice24.of(getItem(3));
        }

        public boolean hasSlice24() {
            return hasItemOfRule(3, Slice24.RULE);
        }

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            Expr.parse(t, l + 1);
            r = t.consumeToken(":");
            if (r) Expr.parse(t, l + 1);
            if (r) Slice24.parse(t, l + 1);
            t.exit(r);
            return r;
        }
    }

    /**
     * ':' ['expr']
     */
    public static final class Slice24 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("slice:2:4", RuleType.Conjunction, false);

        public static Slice24 of(ParseTreeNode node) {
            return new Slice24(node);
        }

        private Slice24(ParseTreeNode node) {
            super(RULE, node);
        }

        public Expr expr() {
            return Expr.of(getItem(1));
        }

        public boolean hasExpr() {
            return hasItemOfRule(1, Expr.RULE);
        }

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = t.consumeToken(":");
            if (r) Expr.parse(t, l + 1);
            t.exit(r);
            return r;
        }
    }
}

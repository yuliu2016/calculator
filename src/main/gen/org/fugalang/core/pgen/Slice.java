package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * slice: ['expr'] 'slice_expr' ['slice_expr'] | 'expr'
 */
public final class Slice extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("slice", RuleType.Disjunction);

    public static Slice of(ParseTreeNode node) {
        return new Slice(node);
    }

    private Slice(ParseTreeNode node) {
        super(RULE, node);
    }

    public Slice1 slice1() {
        return Slice1.of(get(0));
    }

    public boolean hasSlice1() {
        return has(0, Slice1.RULE);
    }

    public Expr expr() {
        return Expr.of(get(1));
    }

    public boolean hasExpr() {
        return has(1, Expr.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = Slice1.parse(t, lv + 1);
        r = r || Expr.parse(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * ['expr'] 'slice_expr' ['slice_expr']
     */
    public static final class Slice1 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("slice:1", RuleType.Conjunction);

        public static Slice1 of(ParseTreeNode node) {
            return new Slice1(node);
        }

        private Slice1(ParseTreeNode node) {
            super(RULE, node);
        }

        public Expr expr() {
            return Expr.of(get(0));
        }

        public boolean hasExpr() {
            return has(0, Expr.RULE);
        }

        public SliceExpr sliceExpr() {
            return SliceExpr.of(get(1));
        }

        public SliceExpr sliceExpr1() {
            return SliceExpr.of(get(2));
        }

        public boolean hasSliceExpr1() {
            return has(2, SliceExpr.RULE);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            Expr.parse(t, lv + 1);
            r = SliceExpr.parse(t, lv + 1);
            if (r) SliceExpr.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}

package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * dict_item: 'expr' ':' 'expr' | '**' 'bitwise_or'
 */
public final class DictItem extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("dict_item", RuleType.Disjunction);

    public static DictItem of(ParseTreeNode node) {
        return new DictItem(node);
    }

    private DictItem(ParseTreeNode node) {
        super(RULE, node);
    }

    public DictItem1 exprExpr() {
        return get(0, DictItem1::of);
    }

    public boolean hasExprExpr() {
        return has(0, DictItem1.RULE);
    }

    public DictItem2 bitwiseOr() {
        return get(1, DictItem2::of);
    }

    public boolean hasBitwiseOr() {
        return has(1, DictItem2.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = DictItem1.parse(t, lv + 1);
        r = r || DictItem2.parse(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * 'expr' ':' 'expr'
     */
    public static final class DictItem1 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("dict_item:1", RuleType.Conjunction);

        public static DictItem1 of(ParseTreeNode node) {
            return new DictItem1(node);
        }

        private DictItem1(ParseTreeNode node) {
            super(RULE, node);
        }

        public Expr expr() {
            return get(0, Expr::of);
        }

        public Expr expr1() {
            return get(2, Expr::of);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (t.recursionGuard(lv)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = Expr.parse(t, lv + 1);
            r = r && t.consume(":");
            r = r && Expr.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }

    /**
     * '**' 'bitwise_or'
     */
    public static final class DictItem2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("dict_item:2", RuleType.Conjunction);

        public static DictItem2 of(ParseTreeNode node) {
            return new DictItem2(node);
        }

        private DictItem2(ParseTreeNode node) {
            super(RULE, node);
        }

        public BitwiseOr bitwiseOr() {
            return get(1, BitwiseOr::of);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (t.recursionGuard(lv)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume("**");
            r = r && BitwiseOr.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}

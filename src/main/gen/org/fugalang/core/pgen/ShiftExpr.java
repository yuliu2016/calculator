package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * shift_expr: 'sum' (('<<' | '>>') 'sum')*
 */
public final class ShiftExpr extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("shift_expr", RuleType.Conjunction);

    public static ShiftExpr of(ParseTreeNode node) {
        return new ShiftExpr(node);
    }

    private ShiftExpr(ParseTreeNode node) {
        super(RULE, node);
    }

    public Sum sum() {
        return Sum.of(getItem(0));
    }

    public List<ShiftExpr2> shiftExpr2List() {
        return getList(1, ShiftExpr2::of);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = Sum.parse(t, lv + 1);
        if (r) parseShiftExpr2List(t, lv);
        t.exit(r);
        return r;
    }

    private static void parseShiftExpr2List(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!ShiftExpr2.parse(t, lv + 1)) break;
            if (t.guardLoopExit(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ('<<' | '>>') 'sum'
     */
    public static final class ShiftExpr2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("shift_expr:2", RuleType.Conjunction);

        public static ShiftExpr2 of(ParseTreeNode node) {
            return new ShiftExpr2(node);
        }

        private ShiftExpr2(ParseTreeNode node) {
            super(RULE, node);
        }

        public ShiftExpr21 shiftExpr21() {
            return ShiftExpr21.of(getItem(0));
        }

        public Sum sum() {
            return Sum.of(getItem(1));
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = ShiftExpr21.parse(t, lv + 1);
            r = r && Sum.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }

    /**
     * '<<' | '>>'
     */
    public static final class ShiftExpr21 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("shift_expr:2:1", RuleType.Disjunction);

        public static ShiftExpr21 of(ParseTreeNode node) {
            return new ShiftExpr21(node);
        }

        private ShiftExpr21(ParseTreeNode node) {
            super(RULE, node);
        }

        public boolean isTokenLshift() {
            return getBoolean(0);
        }

        public boolean isTokenRshift() {
            return getBoolean(1);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consumeToken("<<");
            r = r || t.consumeToken(">>");
            t.exit(r);
            return r;
        }
    }
}

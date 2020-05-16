package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * shift_expr: 'sum' ('shift_op' 'sum')*
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
        return get(0, Sum::of);
    }

    public List<ShiftExpr2> shiftOpSums() {
        return getList(1, ShiftExpr2::of);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = Sum.parse(t, lv + 1);
        if (r) parseShiftOpSums(t, lv);
        t.exit(r);
        return r;
    }

    private static void parseShiftOpSums(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!ShiftExpr2.parse(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * 'shift_op' 'sum'
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

        public ShiftOp shiftOp() {
            return get(0, ShiftOp::of);
        }

        public Sum sum() {
            return get(1, Sum::of);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = ShiftOp.parse(t, lv + 1);
            r = r && Sum.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}

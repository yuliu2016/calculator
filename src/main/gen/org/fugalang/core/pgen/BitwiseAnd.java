package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * bitwise_and: 'shift_expr' ('&' 'shift_expr')*
 */
public final class BitwiseAnd extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("bitwise_and", RuleType.Conjunction, true);

    public static BitwiseAnd of(ParseTreeNode node) {
        return new BitwiseAnd(node);
    }

    private BitwiseAnd(ParseTreeNode node) {
        super(RULE, node);
    }

    public ShiftExpr shiftExpr() {
        return ShiftExpr.of(getItem(0));
    }

    public List<BitwiseAnd2> bitwiseAnd2List() {
        return getList(1, BitwiseAnd2::of);
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = ShiftExpr.parse(t, l + 1);
        if (r) parseBitwiseAnd2List(t, l);
        t.exit(r);
        return r;
    }

    private static void parseBitwiseAnd2List(ParseTree t, int l) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!BitwiseAnd2.parse(t, l + 1)) break;
            if (t.guardLoopExit(p)) break;
        }
        t.exitCollection();
    }

    /**
     * '&' 'shift_expr'
     */
    public static final class BitwiseAnd2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("bitwise_and:2", RuleType.Conjunction, false);

        public static BitwiseAnd2 of(ParseTreeNode node) {
            return new BitwiseAnd2(node);
        }

        private BitwiseAnd2(ParseTreeNode node) {
            super(RULE, node);
        }

        public ShiftExpr shiftExpr() {
            return ShiftExpr.of(getItem(1));
        }

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = t.consumeToken("&");
            r = r && ShiftExpr.parse(t, l + 1);
            t.exit(r);
            return r;
        }
    }
}

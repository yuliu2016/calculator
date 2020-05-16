package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * comparison: 'bitwise_or' ('comp_op' 'bitwise_or')*
 */
public final class Comparison extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("comparison", RuleType.Conjunction);

    public static Comparison of(ParseTreeNode node) {
        return new Comparison(node);
    }

    private Comparison(ParseTreeNode node) {
        super(RULE, node);
    }

    public BitwiseOr bitwiseOr() {
        return get(0, BitwiseOr::of);
    }

    public List<Comparison2> compOpBitwiseOrs() {
        return getList(1, Comparison2::of);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = BitwiseOr.parse(t, lv + 1);
        if (r) parseCompOpBitwiseOrs(t, lv);
        t.exit(r);
        return r;
    }

    private static void parseCompOpBitwiseOrs(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!Comparison2.parse(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * 'comp_op' 'bitwise_or'
     */
    public static final class Comparison2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("comparison:2", RuleType.Conjunction);

        public static Comparison2 of(ParseTreeNode node) {
            return new Comparison2(node);
        }

        private Comparison2(ParseTreeNode node) {
            super(RULE, node);
        }

        public CompOp compOp() {
            return get(0, CompOp::of);
        }

        public BitwiseOr bitwiseOr() {
            return get(1, BitwiseOr::of);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = CompOp.parse(t, lv + 1);
            r = r && BitwiseOr.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}

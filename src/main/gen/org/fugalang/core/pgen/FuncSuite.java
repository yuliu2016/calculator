package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * func_suite: ':' 'expr' | 'block_suite'
 */
public final class FuncSuite extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("func_suite", RuleType.Disjunction);

    public static FuncSuite of(ParseTreeNode node) {
        return new FuncSuite(node);
    }

    private FuncSuite(ParseTreeNode node) {
        super(RULE, node);
    }

    public FuncSuite1 funcSuite1() {
        return FuncSuite1.of(get(0));
    }

    public boolean hasFuncSuite1() {
        return has(0, FuncSuite1.RULE);
    }

    public BlockSuite blockSuite() {
        return BlockSuite.of(get(1));
    }

    public boolean hasBlockSuite() {
        return has(1, BlockSuite.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = FuncSuite1.parse(t, lv + 1);
        r = r || BlockSuite.parse(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * ':' 'expr'
     */
    public static final class FuncSuite1 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("func_suite:1", RuleType.Conjunction);

        public static FuncSuite1 of(ParseTreeNode node) {
            return new FuncSuite1(node);
        }

        private FuncSuite1(ParseTreeNode node) {
            super(RULE, node);
        }

        public Expr expr() {
            return Expr.of(get(1));
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume(":");
            r = r && Expr.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}

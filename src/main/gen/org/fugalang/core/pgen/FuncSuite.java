package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * func_suite: ':' 'expr' | 'block_suite'
 */
public final class FuncSuite extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("func_suite", RuleType.Disjunction, true);

    public static FuncSuite of(ParseTreeNode node) {
        return new FuncSuite(node);
    }

    private FuncSuite(ParseTreeNode node) {
        super(RULE, node);
    }

    public FuncSuite1 funcSuite1() {
        return FuncSuite1.of(getItem(0));
    }

    public boolean hasFuncSuite1() {
        return hasItemOfRule(0, FuncSuite1.RULE);
    }

    public BlockSuite blockSuite() {
        return BlockSuite.of(getItem(1));
    }

    public boolean hasBlockSuite() {
        return hasItemOfRule(1, BlockSuite.RULE);
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = FuncSuite1.parse(t, l + 1);
        r = r || BlockSuite.parse(t, l + 1);
        t.exit(r);
        return r;
    }

    /**
     * ':' 'expr'
     */
    public static final class FuncSuite1 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("func_suite:1", RuleType.Conjunction, false);

        public static FuncSuite1 of(ParseTreeNode node) {
            return new FuncSuite1(node);
        }

        private FuncSuite1(ParseTreeNode node) {
            super(RULE, node);
        }

        public Expr expr() {
            return Expr.of(getItem(1));
        }

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = t.consumeToken(":");
            r = r && Expr.parse(t, l + 1);
            t.exit(r);
            return r;
        }
    }
}

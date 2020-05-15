package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * suite: ':' 'simple_stmt' | 'block_suite'
 */
public final class Suite extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("suite", RuleType.Disjunction);

    public static Suite of(ParseTreeNode node) {
        return new Suite(node);
    }

    private Suite(ParseTreeNode node) {
        super(RULE, node);
    }

    public Suite1 suite1() {
        return Suite1.of(get(0));
    }

    public boolean hasSuite1() {
        return has(0, Suite1.RULE);
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
        r = Suite1.parse(t, lv + 1);
        r = r || BlockSuite.parse(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * ':' 'simple_stmt'
     */
    public static final class Suite1 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("suite:1", RuleType.Conjunction);

        public static Suite1 of(ParseTreeNode node) {
            return new Suite1(node);
        }

        private Suite1(ParseTreeNode node) {
            super(RULE, node);
        }

        public SimpleStmt simpleStmt() {
            return SimpleStmt.of(get(1));
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume(":");
            r = r && SimpleStmt.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}

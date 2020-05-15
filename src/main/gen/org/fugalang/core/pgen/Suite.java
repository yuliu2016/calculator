package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * suite: ':' 'simple_stmt' | 'block_suite'
 */
public final class Suite extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("suite", RuleType.Disjunction, true);

    public static Suite of(ParseTreeNode node) {
        return new Suite(node);
    }

    private Suite(ParseTreeNode node) {
        super(RULE, node);
    }

    public Suite1 suite1() {
        return Suite1.of(getItem(0));
    }

    public boolean hasSuite1() {
        return hasItemOfRule(0, Suite1.RULE);
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
        r = Suite1.parse(t, l + 1);
        r = r || BlockSuite.parse(t, l + 1);
        t.exit(r);
        return r;
    }

    /**
     * ':' 'simple_stmt'
     */
    public static final class Suite1 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("suite:1", RuleType.Conjunction, false);

        public static Suite1 of(ParseTreeNode node) {
            return new Suite1(node);
        }

        private Suite1(ParseTreeNode node) {
            super(RULE, node);
        }

        public SimpleStmt simpleStmt() {
            return SimpleStmt.of(getItem(1));
        }

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = t.consumeToken(":");
            r = r && SimpleStmt.parse(t, l + 1);
            t.exit(r);
            return r;
        }
    }
}

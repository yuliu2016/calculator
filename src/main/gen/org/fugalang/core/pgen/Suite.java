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

    public Suite1 simpleStmt() {
        return get(0, Suite1::of);
    }

    public boolean hasSimpleStmt() {
        return has(0, Suite1.RULE);
    }

    public BlockSuite blockSuite() {
        return get(1, BlockSuite::of);
    }

    public boolean hasBlockSuite() {
        return has(1, BlockSuite.RULE);
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
            return get(1, SimpleStmt::of);
        }
    }
}

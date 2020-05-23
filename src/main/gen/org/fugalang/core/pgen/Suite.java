package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.FugaRules;

/**
 * suite: ':' 'simple_stmt' | 'block_suite'
 */
public final class Suite extends NodeWrapper {

    public Suite(ParseTreeNode node) {
        super(FugaRules.SUITE, node);
    }

    public Suite1 simpleStmt() {
        return get(0, Suite1.class);
    }

    public boolean hasSimpleStmt() {
        return has(0);
    }

    public BlockSuite blockSuite() {
        return get(1, BlockSuite.class);
    }

    public boolean hasBlockSuite() {
        return has(1);
    }

    /**
     * ':' 'simple_stmt'
     */
    public static final class Suite1 extends NodeWrapper {

        public Suite1(ParseTreeNode node) {
            super(FugaRules.SUITE_1, node);
        }

        public SimpleStmt simpleStmt() {
            return get(1, SimpleStmt.class);
        }
    }
}

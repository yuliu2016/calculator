package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * suite:
 * *   | ':' simple_stmt
 * *   | block_suite
 */
public final class Suite extends NodeWrapper {

    public Suite(ParseTreeNode node) {
        super(node);
    }

    public Suite1 colonSimpleStmt() {
        return new Suite1(get(0));
    }

    public boolean hasColonSimpleStmt() {
        return has(0);
    }

    public BlockSuite blockSuite() {
        return new BlockSuite(get(1));
    }

    public boolean hasBlockSuite() {
        return has(1);
    }

    /**
     * ':' simple_stmt
     */
    public static final class Suite1 extends NodeWrapper {

        public Suite1(ParseTreeNode node) {
            super(node);
        }

        public SimpleStmt simpleStmt() {
            return new SimpleStmt(get(1));
        }
    }
}

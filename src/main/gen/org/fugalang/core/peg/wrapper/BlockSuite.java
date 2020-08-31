package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * block_suite (allow_whitespace=false):
 * *   | '{' NEWLINE stmt_list '}'
 * *   | '{' [simple_stmt] '}'
 */
public final class BlockSuite extends NodeWrapper {

    public BlockSuite(ParseTreeNode node) {
        super(node);
    }

    public BlockSuite1 blockSuite1() {
        return new BlockSuite1(get(0));
    }

    public boolean hasBlockSuite1() {
        return has(0);
    }

    public BlockSuite2 blockSuite2() {
        return new BlockSuite2(get(1));
    }

    public boolean hasBlockSuite2() {
        return has(1);
    }

    /**
     * '{' NEWLINE stmt_list '}'
     */
    public static final class BlockSuite1 extends NodeWrapper {

        public BlockSuite1(ParseTreeNode node) {
            super(node);
        }

        public String newline() {
            return get(1, TokenType.NEWLINE);
        }

        public StmtList stmtList() {
            return new StmtList(get(2));
        }
    }

    /**
     * '{' [simple_stmt] '}'
     */
    public static final class BlockSuite2 extends NodeWrapper {

        public BlockSuite2(ParseTreeNode node) {
            super(node);
        }

        public SimpleStmt simpleStmt() {
            return new SimpleStmt(get(1));
        }

        public boolean hasSimpleStmt() {
            return has(1);
        }
    }
}

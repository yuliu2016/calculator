package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;
import org.fugalang.core.token.TokenType;

import java.util.List;

/**
 * block_suite: '{' 'simple_stmt' '}' | '{' 'NEWLINE' 'stmt'+ '}'
 */
public final class BlockSuite extends NodeWrapper {

    public BlockSuite(ParseTreeNode node) {
        super(ParserRules.BLOCK_SUITE, node);
    }

    public BlockSuite1 simpleStmt() {
        return get(0, BlockSuite1::new);
    }

    public boolean hasSimpleStmt() {
        return has(0, ParserRules.BLOCK_SUITE_1);
    }

    public BlockSuite2 blockSuite2() {
        return get(1, BlockSuite2::new);
    }

    public boolean hasBlockSuite2() {
        return has(1, ParserRules.BLOCK_SUITE_2);
    }

    /**
     * '{' 'simple_stmt' '}'
     */
    public static final class BlockSuite1 extends NodeWrapper {

        public BlockSuite1(ParseTreeNode node) {
            super(ParserRules.BLOCK_SUITE_1, node);
        }

        public SimpleStmt simpleStmt() {
            return get(1, SimpleStmt::new);
        }
    }

    /**
     * '{' 'NEWLINE' 'stmt'+ '}'
     */
    public static final class BlockSuite2 extends NodeWrapper {

        public BlockSuite2(ParseTreeNode node) {
            super(ParserRules.BLOCK_SUITE_2, node);
        }

        public String newline() {
            return get(1, TokenType.NEWLINE);
        }

        public List<Stmt> stmts() {
            return getList(2, Stmt::new);
        }
    }
}

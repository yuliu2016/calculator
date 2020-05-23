package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

import java.util.List;

/**
 * block_suite: '{' 'simple_stmt' '}' | '{' 'NEWLINE' 'stmt'+ '}'
 */
public final class BlockSuite extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("block_suite", RuleType.Disjunction);

    public static BlockSuite of(ParseTreeNode node) {
        return new BlockSuite(node);
    }

    private BlockSuite(ParseTreeNode node) {
        super(RULE, node);
    }

    public BlockSuite1 simpleStmt() {
        return get(0, BlockSuite1::of);
    }

    public boolean hasSimpleStmt() {
        return has(0, BlockSuite1.RULE);
    }

    public BlockSuite2 blockSuite2() {
        return get(1, BlockSuite2::of);
    }

    public boolean hasBlockSuite2() {
        return has(1, BlockSuite2.RULE);
    }

    /**
     * '{' 'simple_stmt' '}'
     */
    public static final class BlockSuite1 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("block_suite:1", RuleType.Conjunction);

        public static BlockSuite1 of(ParseTreeNode node) {
            return new BlockSuite1(node);
        }

        private BlockSuite1(ParseTreeNode node) {
            super(RULE, node);
        }

        public SimpleStmt simpleStmt() {
            return get(1, SimpleStmt::of);
        }
    }

    /**
     * '{' 'NEWLINE' 'stmt'+ '}'
     */
    public static final class BlockSuite2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("block_suite:2", RuleType.Conjunction);

        public static BlockSuite2 of(ParseTreeNode node) {
            return new BlockSuite2(node);
        }

        private BlockSuite2(ParseTreeNode node) {
            super(RULE, node);
        }

        public String newline() {
            return get(1, TokenType.NEWLINE);
        }

        public List<Stmt> stmts() {
            return getList(2, Stmt::of);
        }
    }
}

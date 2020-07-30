package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

import java.util.List;

/**
 * block_suite:
 * *   | '{' NEWLINE stmt+ '}'
 * *   | '{' '}'
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

    public BlockSuite2 lbraceRbrace() {
        return new BlockSuite2(get(1));
    }

    public boolean hasLbraceRbrace() {
        return has(1);
    }

    /**
     * '{' NEWLINE stmt+ '}'
     */
    public static final class BlockSuite1 extends NodeWrapper {

        public BlockSuite1(ParseTreeNode node) {
            super(node);
        }

        public String newline() {
            return get(1, TokenType.NEWLINE);
        }

        public List<Stmt> stmts() {
            return getList(2, Stmt::new);
        }
    }

    /**
     * '{' '}'
     */
    public static final class BlockSuite2 extends NodeWrapper {

        public BlockSuite2(ParseTreeNode node) {
            super(node);
        }
    }
}

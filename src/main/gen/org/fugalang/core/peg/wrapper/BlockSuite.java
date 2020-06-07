package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

import java.util.List;

/**
 * block_suite: '{' NEWLINE stmt+ '}'
 */
public final class BlockSuite extends NodeWrapper {

    public BlockSuite(ParseTreeNode node) {
        super(node);
    }

    public String newline() {
        return get(1, TokenType.NEWLINE);
    }

    public List<Stmt> stmts() {
        return getList(2, Stmt::new);
    }
}

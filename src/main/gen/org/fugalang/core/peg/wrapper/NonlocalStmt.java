package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

import java.util.List;

/**
 * nonlocal_stmt:
 * *   | 'nonlocal' ','.NAME+
 */
public final class NonlocalStmt extends NodeWrapper {

    public NonlocalStmt(ParseTreeNode node) {
        super(node);
    }

    public List<String> names() {
        return getList(1, ParseTreeNode::asString);
    }
}

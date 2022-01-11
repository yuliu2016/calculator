package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

import java.util.List;

/**
 * expr_name:
 * *   | '.'.NAME+
 */
public final class ExprName extends NodeWrapper {

    public ExprName(ParseTreeNode node) {
        super(node);
    }

    public List<String> names() {
        return getList(0, ParseTreeNode::asString);
    }
}

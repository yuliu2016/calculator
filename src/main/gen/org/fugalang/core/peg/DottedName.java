package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

import java.util.List;

/**
 * dotted_name: '.'.NAME+
 */
public final class DottedName extends NodeWrapper {

    public DottedName(ParseTreeNode node) {
        super(node);
    }

    public List<String> names() {
        return getList(0, ParseTreeNode::asString);
    }
}

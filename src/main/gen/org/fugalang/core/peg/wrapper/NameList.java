package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

import java.util.List;

/**
 * name_list:
 * *   | ','.NAME+
 */
public final class NameList extends NodeWrapper {

    public NameList(ParseTreeNode node) {
        super(node);
    }

    public List<String> names() {
        return getList(0, ParseTreeNode::asString);
    }
}

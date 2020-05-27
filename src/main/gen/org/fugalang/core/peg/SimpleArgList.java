package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * simple_arg_list: simple_arg+
 */
public final class SimpleArgList extends NodeWrapper {

    public SimpleArgList(ParseTreeNode node) {
        super(node);
    }

    public List<SimpleArg> simpleArgs() {
        return getList(0, SimpleArg.class);
    }
}

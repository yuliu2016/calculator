package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * simple_args:
 * *   | ','.simple_arg+
 */
public final class SimpleArgs extends NodeWrapper {

    public SimpleArgs(ParseTreeNode node) {
        super(node);
    }

    public List<SimpleArg> simpleArgs() {
        return getList(0, SimpleArg::new);
    }
}

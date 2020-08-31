package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * call_arg_list (allow_whitespace=true):
 * *   | ','.call_arg+ [',']
 */
public final class CallArgList extends NodeWrapper {

    public CallArgList(ParseTreeNode node) {
        super(node);
    }

    public List<CallArg> callArgs() {
        return getList(0, CallArg::new);
    }

    public boolean isComma() {
        return is(1);
    }
}

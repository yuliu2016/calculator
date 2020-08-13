package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * invocation:
 * *   | '(' [call_arg_list] ')'
 */
public final class Invocation extends NodeWrapper {

    public Invocation(ParseTreeNode node) {
        super(node);
    }

    public CallArgList callArgList() {
        return new CallArgList(get(1));
    }

    public boolean hasCallArgList() {
        return has(1);
    }
}

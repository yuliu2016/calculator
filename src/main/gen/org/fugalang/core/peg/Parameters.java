package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * parameters: '(' [arglist] ')'
 */
public final class Parameters extends NodeWrapper {

    public Parameters(ParseTreeNode node) {
        super(node);
    }

    public Arglist arglist() {
        return get(1, Arglist.class);
    }

    public boolean hasArglist() {
        return has(1);
    }
}
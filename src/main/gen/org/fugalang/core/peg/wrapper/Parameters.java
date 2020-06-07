package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * parameters:
 * *   | '(' [arglist] ')'
 */
public final class Parameters extends NodeWrapper {

    public Parameters(ParseTreeNode node) {
        super(node);
    }

    public Arglist arglist() {
        return new Arglist(get(1));
    }

    public boolean hasArglist() {
        return has(1);
    }
}

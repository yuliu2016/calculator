package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * subscript: '[' slicelist ']'
 */
public final class Subscript extends NodeWrapper {

    public Subscript(ParseTreeNode node) {
        super(node);
    }

    public Slicelist slicelist() {
        return get(1, Slicelist.class);
    }
}

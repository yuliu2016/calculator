package org.fugalang.core.peg.wrapper;

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
        return new Slicelist(get(1));
    }
}

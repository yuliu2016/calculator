package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * dotted_as_name: dotted_name [as_name]
 */
public final class DottedAsName extends NodeWrapper {

    public DottedAsName(ParseTreeNode node) {
        super(node);
    }

    public DottedName dottedName() {
        return new DottedName(get(0));
    }

    public AsName asName() {
        return new AsName(get(1));
    }

    public boolean hasAsName() {
        return has(1);
    }
}

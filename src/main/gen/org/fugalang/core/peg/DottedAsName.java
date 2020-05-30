package org.fugalang.core.peg;

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
        return get(0, DottedName.class);
    }

    public AsName asName() {
        return get(1, AsName.class);
    }

    public boolean hasAsName() {
        return has(1);
    }
}

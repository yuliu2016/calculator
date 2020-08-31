package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * set_atom:
 * *   | '{' [set_items] '}'
 */
public final class SetAtom extends NodeWrapper {

    public SetAtom(ParseTreeNode node) {
        super(node);
    }

    public SetItems setItems() {
        return new SetItems(get(1));
    }

    public boolean hasSetItems() {
        return has(1);
    }
}

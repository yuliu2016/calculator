package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * list_atom:
 * *   | '[' [list_items] ']'
 */
public final class ListAtom extends NodeWrapper {

    public ListAtom(ParseTreeNode node) {
        super(node);
    }

    public ListItems listItems() {
        return new ListItems(get(1));
    }

    public boolean hasListItems() {
        return has(1);
    }
}

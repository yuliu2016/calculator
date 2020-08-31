package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * tuple_atom:
 * *   | '(' [list_items] ')'
 */
public final class TupleAtom extends NodeWrapper {

    public TupleAtom(ParseTreeNode node) {
        super(node);
    }

    public ListItems listItems() {
        return new ListItems(get(1));
    }

    public boolean hasListItems() {
        return has(1);
    }
}

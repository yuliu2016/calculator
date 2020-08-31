package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * list_iterable:
 * *   | '[' list_iterator ']'
 */
public final class ListIterable extends NodeWrapper {

    public ListIterable(ParseTreeNode node) {
        super(node);
    }

    public ListIterator listIterator() {
        return new ListIterator(get(1));
    }
}

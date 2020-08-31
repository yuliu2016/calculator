package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * dict_iterator:
 * *   | dict_item iterator
 */
public final class DictIterator extends NodeWrapper {

    public DictIterator(ParseTreeNode node) {
        super(node);
    }

    public DictItem dictItem() {
        return new DictItem(get(0));
    }

    public Iterator iterator() {
        return new Iterator(get(1));
    }
}

package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * dict_iter: '{' dict_item iterator '}'
 */
public final class DictIter extends NodeWrapper {

    public DictIter(ParseTreeNode node) {
        super(node);
    }

    public DictItem dictItem() {
        return new DictItem(get(1));
    }

    public Iterator iterator() {
        return new Iterator(get(2));
    }
}

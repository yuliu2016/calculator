package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * dict_iterable:
 * *   | '{' dict_iterator '}'
 */
public final class DictIterable extends NodeWrapper {

    public DictIterable(ParseTreeNode node) {
        super(node);
    }

    public DictIterator dictIterator() {
        return new DictIterator(get(1));
    }
}

package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * dict_atom: '{' [dict_items] '}'
 */
public final class DictAtom extends NodeWrapper {

    public DictAtom(ParseTreeNode node) {
        super(node);
    }

    public DictItems dictItems() {
        return new DictItems(get(1));
    }

    public boolean hasDictItems() {
        return has(1);
    }
}

package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * dict_items: ','.dict_item+ [',']
 */
public final class DictItems extends NodeWrapper {

    public DictItems(ParseTreeNode node) {
        super(node);
    }

    public List<DictItem> dictItems() {
        return getList(0, DictItem::new);
    }

    public boolean isComma() {
        return is(1);
    }
}

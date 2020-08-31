package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * list_items:
 * *   | ','.list_item+ [',']
 */
public final class ListItems extends NodeWrapper {

    public ListItems(ParseTreeNode node) {
        super(node);
    }

    public List<ListItem> listItems() {
        return getList(0, ListItem::new);
    }

    public boolean isComma() {
        return is(1);
    }
}

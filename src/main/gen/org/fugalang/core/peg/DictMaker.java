package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * dict_maker: ','.dict_item+ [',']
 */
public final class DictMaker extends NodeWrapper {

    public DictMaker(ParseTreeNode node) {
        super(node);
    }

    public List<DictItem> dictItems() {
        return getList(0, DictItem.class);
    }

    public boolean isComma() {
        return is(1);
    }
}

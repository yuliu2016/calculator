package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * dict_maker: dict_item (',' dict_item)* [',']
 */
public final class DictMaker extends NodeWrapper {

    public DictMaker(ParseTreeNode node) {
        super(node);
    }

    public DictItem dictItem() {
        return get(0, DictItem.class);
    }

    public List<DictMaker2> commaDictItems() {
        return getList(1, DictMaker2.class);
    }

    public boolean isComma() {
        return is(2);
    }

    /**
     * ',' dict_item
     */
    public static final class DictMaker2 extends NodeWrapper {

        public DictMaker2(ParseTreeNode node) {
            super(node);
        }

        public DictItem dictItem() {
            return get(1, DictItem.class);
        }
    }
}

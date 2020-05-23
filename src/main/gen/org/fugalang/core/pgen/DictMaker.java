package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * dict_maker: 'dict_item' (',' 'dict_item')* [',']
 */
public final class DictMaker extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("dict_maker", RuleType.Conjunction);

    public static DictMaker of(ParseTreeNode node) {
        return new DictMaker(node);
    }

    private DictMaker(ParseTreeNode node) {
        super(RULE, node);
    }

    public DictItem dictItem() {
        return get(0, DictItem::of);
    }

    public List<DictMaker2> dictItems() {
        return getList(1, DictMaker2::of);
    }

    public boolean isComma() {
        return is(2);
    }

    /**
     * ',' 'dict_item'
     */
    public static final class DictMaker2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("dict_maker:2", RuleType.Conjunction);

        public static DictMaker2 of(ParseTreeNode node) {
            return new DictMaker2(node);
        }

        private DictMaker2(ParseTreeNode node) {
            super(RULE, node);
        }

        public DictItem dictItem() {
            return get(1, DictItem::of);
        }
    }
}

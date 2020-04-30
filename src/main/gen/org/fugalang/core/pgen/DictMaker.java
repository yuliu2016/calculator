package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;
import org.fugalang.core.parser.DisjunctionRule;

// dict_maker: 'dict_item' ('comp_for' | (',' 'dict_item')* [','])
public final class DictMaker extends ConjunctionRule {
    private final DictItem dictItem;
    private final DictMaker2Group dictMaker2Group;

    public DictMaker(
            DictItem dictItem,
            DictMaker2Group dictMaker2Group
    ) {
        this.dictItem = dictItem;
        this.dictMaker2Group = dictMaker2Group;

        addRequired("dictItem", dictItem);
        addRequired("dictMaker2Group", dictMaker2Group);
    }

    public DictItem dictItem() {
        return dictItem;
    }

    public DictMaker2Group dictMaker2Group() {
        return dictMaker2Group;
    }

    // 'comp_for' | (',' 'dict_item')* [',']
    public static final class DictMaker2Group extends DisjunctionRule {
        private final CompFor compFor;
        private final DictMaker2Group2 dictMaker2Group2;

        public DictMaker2Group(
                CompFor compFor,
                DictMaker2Group2 dictMaker2Group2
        ) {
            this.compFor = compFor;
            this.dictMaker2Group2 = dictMaker2Group2;

            addChoice("compFor", compFor);
            addChoice("dictMaker2Group2", dictMaker2Group2);
        }

        public CompFor compFor() {
            return compFor;
        }

        public DictMaker2Group2 dictMaker2Group2() {
            return dictMaker2Group2;
        }
    }

    // (',' 'dict_item')* [',']
    public static final class DictMaker2Group2 extends ConjunctionRule {
        private final List<DictMaker2Group21Group> dictMaker2Group21GroupList;
        private final boolean isTokenComma;

        public DictMaker2Group2(
                List<DictMaker2Group21Group> dictMaker2Group21GroupList,
                boolean isTokenComma
        ) {
            this.dictMaker2Group21GroupList = dictMaker2Group21GroupList;
            this.isTokenComma = isTokenComma;

            addRequired("dictMaker2Group21GroupList", dictMaker2Group21GroupList);
            addRequired("isTokenComma", isTokenComma);
        }

        public List<DictMaker2Group21Group> dictMaker2Group21GroupList() {
            return dictMaker2Group21GroupList;
        }

        public boolean isTokenComma() {
            return isTokenComma;
        }
    }

    // ',' 'dict_item'
    public static final class DictMaker2Group21Group extends ConjunctionRule {
        private final boolean isTokenComma;
        private final DictItem dictItem;

        public DictMaker2Group21Group(
                boolean isTokenComma,
                DictItem dictItem
        ) {
            this.isTokenComma = isTokenComma;
            this.dictItem = dictItem;

            addRequired("isTokenComma", isTokenComma);
            addRequired("dictItem", dictItem);
        }

        public boolean isTokenComma() {
            return isTokenComma;
        }

        public DictItem dictItem() {
            return dictItem;
        }
    }
}

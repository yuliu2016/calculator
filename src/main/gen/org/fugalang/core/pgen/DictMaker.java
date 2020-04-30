package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;
import org.fugalang.core.parser.DisjunctionRule;

// dict_maker: 'dict_item' ('comp_for' | (',' 'dict_item')* [','])
public final class DictMaker extends ConjunctionRule {
    private final DictItem dictItem;
    private final DictMaker2 dictMaker2;

    public DictMaker(
            DictItem dictItem,
            DictMaker2 dictMaker2
    ) {
        this.dictItem = dictItem;
        this.dictMaker2 = dictMaker2;
    }

    @Override
    protected void buildRule() {
        addRequired("dictItem", dictItem);
        addRequired("dictMaker2", dictMaker2);
    }

    public DictItem dictItem() {
        return dictItem;
    }

    public DictMaker2 dictMaker2() {
        return dictMaker2;
    }

    // 'comp_for' | (',' 'dict_item')* [',']
    public static final class DictMaker2 extends DisjunctionRule {
        private final CompFor compFor;
        private final DictMaker22 dictMaker22;

        public DictMaker2(
                CompFor compFor,
                DictMaker22 dictMaker22
        ) {
            this.compFor = compFor;
            this.dictMaker22 = dictMaker22;
        }

        @Override
        protected void buildRule() {
            addChoice("compFor", compFor);
            addChoice("dictMaker22", dictMaker22);
        }

        public CompFor compFor() {
            return compFor;
        }

        public DictMaker22 dictMaker22() {
            return dictMaker22;
        }
    }

    // (',' 'dict_item')* [',']
    public static final class DictMaker22 extends ConjunctionRule {
        private final List<DictMaker221> dictMaker221List;
        private final boolean isTokenComma;

        public DictMaker22(
                List<DictMaker221> dictMaker221List,
                boolean isTokenComma
        ) {
            this.dictMaker221List = dictMaker221List;
            this.isTokenComma = isTokenComma;
        }

        @Override
        protected void buildRule() {
            addRequired("dictMaker221List", dictMaker221List);
            addRequired("isTokenComma", isTokenComma);
        }

        public List<DictMaker221> dictMaker221List() {
            return dictMaker221List;
        }

        public boolean isTokenComma() {
            return isTokenComma;
        }
    }

    // ',' 'dict_item'
    public static final class DictMaker221 extends ConjunctionRule {
        private final boolean isTokenComma;
        private final DictItem dictItem;

        public DictMaker221(
                boolean isTokenComma,
                DictItem dictItem
        ) {
            this.isTokenComma = isTokenComma;
            this.dictItem = dictItem;
        }

        @Override
        protected void buildRule() {
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

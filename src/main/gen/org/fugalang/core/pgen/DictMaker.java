package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import org.fugalang.core.parser.DisjunctionRule;
import org.fugalang.core.parser.ParseTree;

import java.util.List;

/**
 * dict_maker: 'dict_item' ('comp_for' | (',' 'dict_item')* [','])
 */
public final class DictMaker extends ConjunctionRule {
    public static final String RULE_NAME = "dict_maker";

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
        setExplicitName(RULE_NAME);
        addRequired("dictItem", dictItem);
        addRequired("dictMaker2", dictMaker2);
    }

    public DictItem dictItem() {
        return dictItem;
    }

    public DictMaker2 dictMaker2() {
        return dictMaker2;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        boolean result;

        result = DictItem.parse(parseTree, level + 1);
        result = result && DictMaker2.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * 'comp_for' | (',' 'dict_item')* [',']
     */
    public static final class DictMaker2 extends DisjunctionRule {
        public static final String RULE_NAME = "dict_maker:2";

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
            setImpliedName(RULE_NAME);
            addChoice("compFor", compFor);
            addChoice("dictMaker22", dictMaker22);
        }

        public CompFor compFor() {
            return compFor;
        }

        public boolean hasCompFor() {
            return compFor() != null;
        }

        public DictMaker22 dictMaker22() {
            return dictMaker22;
        }

        public boolean hasDictMaker22() {
            return dictMaker22() != null;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            result = CompFor.parse(parseTree, level + 1);
            result = result || DictMaker22.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * (',' 'dict_item')* [',']
     */
    public static final class DictMaker22 extends ConjunctionRule {
        public static final String RULE_NAME = "dict_maker:2:2";

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
            setImpliedName(RULE_NAME);
            addRequired("dictMaker221List", dictMaker221List);
            addRequired("isTokenComma", isTokenComma);
        }

        public List<DictMaker221> dictMaker221List() {
            return dictMaker221List;
        }

        public boolean isTokenComma() {
            return isTokenComma;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            parseTree.enterCollection();
            while (true) {
                var pos = parseTree.position();
                if (!DictMaker221.parse(parseTree, level + 1) ||
                        parseTree.guardLoopExit(pos)) {
                    break;
                }
            }
            parseTree.exitCollection();
            result = parseTree.consumeTokenLiteral(",");

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * ',' 'dict_item'
     */
    public static final class DictMaker221 extends ConjunctionRule {
        public static final String RULE_NAME = "dict_maker:2:2:1";

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
            setImpliedName(RULE_NAME);
            addRequired("isTokenComma", isTokenComma);
            addRequired("dictItem", dictItem);
        }

        public boolean isTokenComma() {
            return isTokenComma;
        }

        public DictItem dictItem() {
            return dictItem;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            result = parseTree.consumeTokenLiteral(",");
            result = result && DictItem.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}

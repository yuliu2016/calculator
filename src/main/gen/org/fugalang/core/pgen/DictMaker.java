package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * dict_maker: 'dict_item' ('comp_for' | (',' 'dict_item')* [','])
 */
public final class DictMaker extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("dict_maker", RuleType.Conjunction, true);

    public static DictMaker of(ParseTreeNode node) {
        return new DictMaker(node);
    }

    private DictMaker(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addRequired("dictItem", dictItem());
        addRequired("dictMaker2", dictMaker2());
    }

    public DictItem dictItem() {
        var element = getItem(0);
        if (!element.isPresent()) return null;
        return DictItem.of(element);
    }

    public DictMaker2 dictMaker2() {
        var element = getItem(1);
        if (!element.isPresent()) return null;
        return DictMaker2.of(element);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = DictItem.parse(parseTree, level + 1);
        result = result && DictMaker2.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * 'comp_for' | (',' 'dict_item')* [',']
     */
    public static final class DictMaker2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("dict_maker:2", RuleType.Disjunction, false);

        public static DictMaker2 of(ParseTreeNode node) {
            return new DictMaker2(node);
        }

        private DictMaker2(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addChoice("compFor", compFor());
            addChoice("dictMaker22", dictMaker22());
        }

        public CompFor compFor() {
            var element = getItem(0);
            if (!element.isPresent()) return null;
            return CompFor.of(element);
        }

        public boolean hasCompFor() {
            return compFor() != null;
        }

        public DictMaker22 dictMaker22() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return DictMaker22.of(element);
        }

        public boolean hasDictMaker22() {
            return dictMaker22() != null;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
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
    public static final class DictMaker22 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("dict_maker:2:2", RuleType.Conjunction, false);

        public static DictMaker22 of(ParseTreeNode node) {
            return new DictMaker22(node);
        }

        private DictMaker22(ParseTreeNode node) {
            super(RULE, node);
        }

        private List<DictMaker221> dictMaker221List;

        @Override
        protected void buildRule() {
            addRequired("dictMaker221List", dictMaker221List());
            addRequired("isTokenComma", isTokenComma());
        }

        public List<DictMaker221> dictMaker221List() {
            return dictMaker221List;
        }

        public boolean isTokenComma() {
            var element = getItem(1);
            return element.asBoolean();
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
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
    public static final class DictMaker221 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("dict_maker:2:2:1", RuleType.Conjunction, false);

        public static DictMaker221 of(ParseTreeNode node) {
            return new DictMaker221(node);
        }

        private DictMaker221(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenComma", isTokenComma());
            addRequired("dictItem", dictItem());
        }

        public boolean isTokenComma() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public DictItem dictItem() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return DictItem.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral(",");
            result = result && DictItem.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}

package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * dict_maker: 'dict_item' ['comp_for' | (',' 'dict_item')+ [',']]
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
        addRequired(dictItem());
        addOptional(dictMaker2());
    }

    public DictItem dictItem() {
        var element = getItem(0);
        element.failIfAbsent(DictItem.RULE);
        return DictItem.of(element);
    }

    public DictMaker2 dictMaker2() {
        var element = getItem(1);
        element.failIfAbsent(DictMaker2.RULE);
        return DictMaker2.of(element);
    }

    public DictMaker2 dictMaker2OrNull() {
        var element = getItem(1);
        if (!element.isPresent(DictMaker2.RULE)) {
            return null;
        }
        return DictMaker2.of(element);
    }

    public boolean hasDictMaker2() {
        var element = getItem(1);
        return element.isPresent(DictMaker2.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = DictItem.parse(parseTree, level + 1);
        if (result) DictMaker2.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * 'comp_for' | (',' 'dict_item')+ [',']
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
            addChoice(compFor());
            addChoice(dictMaker22());
        }

        public CompFor compFor() {
            var element = getItem(0);
            element.failIfAbsent(CompFor.RULE);
            return CompFor.of(element);
        }

        public CompFor compForOrNull() {
            var element = getItem(0);
            if (!element.isPresent(CompFor.RULE)) {
                return null;
            }
            return CompFor.of(element);
        }

        public boolean hasCompFor() {
            var element = getItem(0);
            return element.isPresent(CompFor.RULE);
        }

        public DictMaker22 dictMaker22() {
            var element = getItem(1);
            element.failIfAbsent(DictMaker22.RULE);
            return DictMaker22.of(element);
        }

        public DictMaker22 dictMaker22OrNull() {
            var element = getItem(1);
            if (!element.isPresent(DictMaker22.RULE)) {
                return null;
            }
            return DictMaker22.of(element);
        }

        public boolean hasDictMaker22() {
            var element = getItem(1);
            return element.isPresent(DictMaker22.RULE);
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
     * (',' 'dict_item')+ [',']
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
            addRequired(dictMaker221List());
            addOptional(isTokenComma(), ",");
        }

        public List<DictMaker221> dictMaker221List() {
            if (dictMaker221List != null) {
                return dictMaker221List;
            }
            List<DictMaker221> result = null;
            var element = getItem(0);
            for (var node : element.asCollection()) {
                if (result == null) {
                    result = new ArrayList<>();
                }
                result.add(DictMaker221.of(node));
            }
            dictMaker221List = result == null ? Collections.emptyList() : result;
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
            var firstItem = DictMaker221.parse(parseTree, level + 1);
            result = firstItem;
            if (firstItem) while (true) {
                var pos = parseTree.position();
                if (!DictMaker221.parse(parseTree, level + 1) ||
                        parseTree.guardLoopExit(pos)) {
                    break;
                }
            }
            parseTree.exitCollection();
            if (result) parseTree.consumeToken(",");

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
            addRequired(isTokenComma(), ",");
            addRequired(dictItem());
        }

        public boolean isTokenComma() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public DictItem dictItem() {
            var element = getItem(1);
            element.failIfAbsent(DictItem.RULE);
            return DictItem.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(",");
            result = result && DictItem.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}

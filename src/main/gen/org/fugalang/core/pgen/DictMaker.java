package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * dict_maker: 'dict_item' (',' 'dict_item')* [',']
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

    private List<DictMaker2> dictMaker2List;

    @Override
    protected void buildRule() {
        addRequired(dictItem());
        addRequired(dictMaker2List());
        addOptional(isTokenComma(), ",");
    }

    public DictItem dictItem() {
        var element = getItem(0);
        element.failIfAbsent(DictItem.RULE);
        return DictItem.of(element);
    }

    public List<DictMaker2> dictMaker2List() {
        if (dictMaker2List != null) {
            return dictMaker2List;
        }
        List<DictMaker2> result = null;
        var element = getItem(1);
        for (var node : element.asCollection()) {
            if (result == null) {
                result = new ArrayList<>();
            }
            result.add(DictMaker2.of(node));
        }
        dictMaker2List = result == null ? Collections.emptyList() : result;
        return dictMaker2List;
    }

    public boolean isTokenComma() {
        var element = getItem(2);
        return element.asBoolean();
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = DictItem.parse(parseTree, level + 1);
        if (result) parseDictMaker2List(parseTree, level + 1);
        if (result) parseTree.consumeToken(",");

        parseTree.exit(level, marker, result);
        return result;
    }

    private static void parseDictMaker2List(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return;
        }
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!DictMaker2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();
    }

    /**
     * ',' 'dict_item'
     */
    public static final class DictMaker2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("dict_maker:2", RuleType.Conjunction, false);

        public static DictMaker2 of(ParseTreeNode node) {
            return new DictMaker2(node);
        }

        private DictMaker2(ParseTreeNode node) {
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

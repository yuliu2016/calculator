package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

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

    public DictItem dictItem() {
        return DictItem.of(getItem(0));
    }

    public List<DictMaker2> dictMaker2List() {
        return getList(1, DictMaker2::of);
    }

    public boolean isTokenComma() {
        return getBoolean(2);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = DictItem.parse(parseTree, level + 1);
        if (result) parseDictMaker2List(parseTree, level);
        if (result) parseTree.consumeToken(",");

        parseTree.exit(result);
        return result;
    }

    private static void parseDictMaker2List(ParseTree parseTree, int level) {
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!DictMaker2.parse(parseTree, level + 1)) break;
            if (parseTree.guardLoopExit(pos)) break;
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

        public DictItem dictItem() {
            return DictItem.of(getItem(1));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(",");
            result = result && DictItem.parse(parseTree, level + 1);

            parseTree.exit(result);
            return result;
        }
    }
}

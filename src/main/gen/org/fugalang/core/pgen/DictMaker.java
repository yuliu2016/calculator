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

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = DictItem.parse(t, l + 1);
        if (r) parseDictMaker2List(t, l);
        if (r) t.consumeToken(",");
        t.exit(r);
        return r;
    }

    private static void parseDictMaker2List(ParseTree t, int l) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!DictMaker2.parse(t, l + 1)) break;
            if (t.guardLoopExit(p)) break;
        }
        t.exitCollection();
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

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = t.consumeToken(",");
            r = r && DictItem.parse(t, l + 1);
            t.exit(r);
            return r;
        }
    }
}

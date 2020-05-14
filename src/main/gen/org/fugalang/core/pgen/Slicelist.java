package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * slicelist: 'slice' (',' 'slice')* [',']
 */
public final class Slicelist extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("slicelist", RuleType.Conjunction, true);

    public static Slicelist of(ParseTreeNode node) {
        return new Slicelist(node);
    }

    private Slicelist(ParseTreeNode node) {
        super(RULE, node);
    }

    public Slice slice() {
        return Slice.of(getItem(0));
    }

    public List<Slicelist2> slicelist2List() {
        return getList(1, Slicelist2::of);
    }

    public boolean isTokenComma() {
        return getBoolean(2);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = Slice.parse(parseTree, level + 1);
        if (result) parseSlicelist2List(parseTree, level + 1);
        if (result) parseTree.consumeToken(",");

        parseTree.exit(level, marker, result);
        return result;
    }

    private static void parseSlicelist2List(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return;
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!Slicelist2.parse(parseTree, level + 1)) break;
            if (parseTree.guardLoopExit(pos)) break;
        }
        parseTree.exitCollection();
    }

    /**
     * ',' 'slice'
     */
    public static final class Slicelist2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("slicelist:2", RuleType.Conjunction, false);

        public static Slicelist2 of(ParseTreeNode node) {
            return new Slicelist2(node);
        }

        private Slicelist2(ParseTreeNode node) {
            super(RULE, node);
        }

        public Slice slice() {
            return Slice.of(getItem(1));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(",");
            result = result && Slice.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}

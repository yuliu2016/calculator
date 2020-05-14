package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * arglist: 'argument' (',' 'argument')* [',']
 */
public final class Arglist extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("arglist", RuleType.Conjunction, true);

    public static Arglist of(ParseTreeNode node) {
        return new Arglist(node);
    }

    private Arglist(ParseTreeNode node) {
        super(RULE, node);
    }

    public Argument argument() {
        return Argument.of(getItem(0));
    }

    public List<Arglist2> arglist2List() {
        return getList(1, Arglist2::of);
    }

    public boolean isTokenComma() {
        return getBoolean(2);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = Argument.parse(parseTree, level + 1);
        if (result) parseArglist2List(parseTree, level);
        if (result) parseTree.consumeToken(",");

        parseTree.exit(result);
        return result;
    }

    private static void parseArglist2List(ParseTree parseTree, int level) {
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!Arglist2.parse(parseTree, level + 1)) break;
            if (parseTree.guardLoopExit(pos)) break;
        }
        parseTree.exitCollection();
    }

    /**
     * ',' 'argument'
     */
    public static final class Arglist2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("arglist:2", RuleType.Conjunction, false);

        public static Arglist2 of(ParseTreeNode node) {
            return new Arglist2(node);
        }

        private Arglist2(ParseTreeNode node) {
            super(RULE, node);
        }

        public Argument argument() {
            return Argument.of(getItem(1));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(",");
            result = result && Argument.parse(parseTree, level + 1);

            parseTree.exit(result);
            return result;
        }
    }
}

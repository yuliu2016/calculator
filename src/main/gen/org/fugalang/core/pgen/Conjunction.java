package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * conjunction: 'inversion' ('and' 'inversion')*
 */
public final class Conjunction extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("conjunction", RuleType.Conjunction, true);

    public static Conjunction of(ParseTreeNode node) {
        return new Conjunction(node);
    }

    private Conjunction(ParseTreeNode node) {
        super(RULE, node);
    }

    public Inversion inversion() {
        return Inversion.of(getItem(0));
    }

    public List<Conjunction2> conjunction2List() {
        return getList(1, Conjunction2::of);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = Inversion.parse(parseTree, level + 1);
        if (result) parseConjunction2List(parseTree, level);

        parseTree.exit(result);
        return result;
    }

    private static void parseConjunction2List(ParseTree parseTree, int level) {
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!Conjunction2.parse(parseTree, level + 1)) break;
            if (parseTree.guardLoopExit(pos)) break;
        }
        parseTree.exitCollection();
    }

    /**
     * 'and' 'inversion'
     */
    public static final class Conjunction2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("conjunction:2", RuleType.Conjunction, false);

        public static Conjunction2 of(ParseTreeNode node) {
            return new Conjunction2(node);
        }

        private Conjunction2(ParseTreeNode node) {
            super(RULE, node);
        }

        public Inversion inversion() {
            return Inversion.of(getItem(1));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("and");
            result = result && Inversion.parse(parseTree, level + 1);

            parseTree.exit(result);
            return result;
        }
    }
}

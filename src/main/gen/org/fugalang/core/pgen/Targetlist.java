package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * targetlist: 'target' (',' 'target')* [',']
 */
public final class Targetlist extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("targetlist", RuleType.Conjunction, true);

    public static Targetlist of(ParseTreeNode node) {
        return new Targetlist(node);
    }

    private Targetlist(ParseTreeNode node) {
        super(RULE, node);
    }

    public Target target() {
        return Target.of(getItem(0));
    }

    public List<Targetlist2> targetlist2List() {
        return getList(1, Targetlist2::of);
    }

    public boolean isTokenComma() {
        return getBoolean(2);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = Target.parse(parseTree, level + 1);
        if (result) parseTargetlist2List(parseTree, level);
        if (result) parseTree.consumeToken(",");

        parseTree.exit(result);
        return result;
    }

    private static void parseTargetlist2List(ParseTree parseTree, int level) {
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!Targetlist2.parse(parseTree, level + 1)) break;
            if (parseTree.guardLoopExit(pos)) break;
        }
        parseTree.exitCollection();
    }

    /**
     * ',' 'target'
     */
    public static final class Targetlist2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("targetlist:2", RuleType.Conjunction, false);

        public static Targetlist2 of(ParseTreeNode node) {
            return new Targetlist2(node);
        }

        private Targetlist2(ParseTreeNode node) {
            super(RULE, node);
        }

        public Target target() {
            return Target.of(getItem(1));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(",");
            result = result && Target.parse(parseTree, level + 1);

            parseTree.exit(result);
            return result;
        }
    }
}

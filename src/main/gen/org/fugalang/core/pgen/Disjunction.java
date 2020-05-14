package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * disjunction: 'conjunction' ('or' 'conjunction')*
 */
public final class Disjunction extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("disjunction", RuleType.Conjunction, true);

    public static Disjunction of(ParseTreeNode node) {
        return new Disjunction(node);
    }

    private Disjunction(ParseTreeNode node) {
        super(RULE, node);
    }

    public Conjunction conjunction() {
        return Conjunction.of(getItem(0));
    }

    public List<Disjunction2> disjunction2List() {
        return getList(1, Disjunction2::of);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = Conjunction.parse(parseTree, level + 1);
        if (result) parseDisjunction2List(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    private static void parseDisjunction2List(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return;
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!Disjunction2.parse(parseTree, level + 1)) break;
            if (parseTree.guardLoopExit(pos)) break;
        }
        parseTree.exitCollection();
    }

    /**
     * 'or' 'conjunction'
     */
    public static final class Disjunction2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("disjunction:2", RuleType.Conjunction, false);

        public static Disjunction2 of(ParseTreeNode node) {
            return new Disjunction2(node);
        }

        private Disjunction2(ParseTreeNode node) {
            super(RULE, node);
        }

        public Conjunction conjunction() {
            return Conjunction.of(getItem(1));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("or");
            result = result && Conjunction.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}

package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * sum: 'term' (('+' | '-') 'term')*
 */
public final class Sum extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("sum", RuleType.Conjunction, true);

    public static Sum of(ParseTreeNode node) {
        return new Sum(node);
    }

    private Sum(ParseTreeNode node) {
        super(RULE, node);
    }

    public Term term() {
        return Term.of(getItem(0));
    }

    public List<Sum2> sum2List() {
        return getList(1, Sum2::of);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = Term.parse(parseTree, level + 1);
        if (result) parseSum2List(parseTree, level);

        parseTree.exit(result);
        return result;
    }

    private static void parseSum2List(ParseTree parseTree, int level) {
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!Sum2.parse(parseTree, level + 1)) break;
            if (parseTree.guardLoopExit(pos)) break;
        }
        parseTree.exitCollection();
    }

    /**
     * ('+' | '-') 'term'
     */
    public static final class Sum2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("sum:2", RuleType.Conjunction, false);

        public static Sum2 of(ParseTreeNode node) {
            return new Sum2(node);
        }

        private Sum2(ParseTreeNode node) {
            super(RULE, node);
        }

        public Sum21 sum21() {
            return Sum21.of(getItem(0));
        }

        public Term term() {
            return Term.of(getItem(1));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            parseTree.enter(level, RULE);
            boolean result;

            result = Sum21.parse(parseTree, level + 1);
            result = result && Term.parse(parseTree, level + 1);

            parseTree.exit(result);
            return result;
        }
    }

    /**
     * '+' | '-'
     */
    public static final class Sum21 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("sum:2:1", RuleType.Disjunction, false);

        public static Sum21 of(ParseTreeNode node) {
            return new Sum21(node);
        }

        private Sum21(ParseTreeNode node) {
            super(RULE, node);
        }

        public boolean isTokenPlus() {
            return getBoolean(0);
        }

        public boolean isTokenMinus() {
            return getBoolean(1);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("+");
            result = result || parseTree.consumeToken("-");

            parseTree.exit(result);
            return result;
        }
    }
}

package org.fugalang.core.calculator.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * term: 'factor' (('*' | '/' | '%') 'factor')*
 */
public final class Term extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("term", RuleType.Conjunction, true);

    public static Term of(ParseTreeNode node) {
        return new Term(node);
    }

    private Term(ParseTreeNode node) {
        super(RULE, node);
    }

    public Factor factor() {
        return Factor.of(getItem(0));
    }

    public List<Term2> term2List() {
        return getList(1, Term2::of);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = Factor.parse(parseTree, level + 1);
        if (result) parseTerm2List(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    private static void parseTerm2List(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return;
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!Term2.parse(parseTree, level + 1)) break;
            if (parseTree.guardLoopExit(pos)) break;
        }
        parseTree.exitCollection();
    }

    /**
     * ('*' | '/' | '%') 'factor'
     */
    public static final class Term2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("term:2", RuleType.Conjunction, false);

        public static Term2 of(ParseTreeNode node) {
            return new Term2(node);
        }

        private Term2(ParseTreeNode node) {
            super(RULE, node);
        }

        public Term21 term21() {
            return Term21.of(getItem(0));
        }

        public Factor factor() {
            return Factor.of(getItem(1));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = Term21.parse(parseTree, level + 1);
            result = result && Factor.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * '*' | '/' | '%'
     */
    public static final class Term21 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("term:2:1", RuleType.Disjunction, false);

        public static Term21 of(ParseTreeNode node) {
            return new Term21(node);
        }

        private Term21(ParseTreeNode node) {
            super(RULE, node);
        }

        public boolean isTokenTimes() {
            return getBoolean(0);
        }

        public boolean isTokenDiv() {
            return getBoolean(1);
        }

        public boolean isTokenModulus() {
            return getBoolean(2);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("*");
            result = result || parseTree.consumeToken("/");
            result = result || parseTree.consumeToken("%");

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}

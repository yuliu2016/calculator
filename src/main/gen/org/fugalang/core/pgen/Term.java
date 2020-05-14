package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * term: 'pipeline' (('*' | '@' | '/' | '%' | '//') 'pipeline')*
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

    public Pipeline pipeline() {
        return Pipeline.of(getItem(0));
    }

    public List<Term2> term2List() {
        return getList(1, Term2::of);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = Pipeline.parse(parseTree, level + 1);
        if (result) parseTerm2List(parseTree, level);

        parseTree.exit(result);
        return result;
    }

    private static void parseTerm2List(ParseTree parseTree, int level) {
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!Term2.parse(parseTree, level + 1)) break;
            if (parseTree.guardLoopExit(pos)) break;
        }
        parseTree.exitCollection();
    }

    /**
     * ('*' | '@' | '/' | '%' | '//') 'pipeline'
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

        public Pipeline pipeline() {
            return Pipeline.of(getItem(1));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            parseTree.enter(level, RULE);
            boolean result;

            result = Term21.parse(parseTree, level + 1);
            result = result && Pipeline.parse(parseTree, level + 1);

            parseTree.exit(result);
            return result;
        }
    }

    /**
     * '*' | '@' | '/' | '%' | '//'
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

        public boolean isTokenMatrixTimes() {
            return getBoolean(1);
        }

        public boolean isTokenDiv() {
            return getBoolean(2);
        }

        public boolean isTokenModulus() {
            return getBoolean(3);
        }

        public boolean isTokenFloorDiv() {
            return getBoolean(4);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("*");
            result = result || parseTree.consumeToken("@");
            result = result || parseTree.consumeToken("/");
            result = result || parseTree.consumeToken("%");
            result = result || parseTree.consumeToken("//");

            parseTree.exit(result);
            return result;
        }
    }
}

package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * term: 'factor' (('*' | '@' | '/' | '%' | '//') 'factor')*
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

    private List<Term2> term2List;

    @Override
    protected void buildRule() {
        addRequired("factor", factor());
        addRequired("term2List", term2List());
    }

    public Factor factor() {
        var element = getItem(0);
        if (!element.isPresent()) return null;
        return Factor.of(element);
    }

    public List<Term2> term2List() {
        return term2List;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = Factor.parse(parseTree, level + 1);
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!Term2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * ('*' | '@' | '/' | '%' | '//') 'factor'
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

        @Override
        protected void buildRule() {
            addRequired("term21", term21());
            addRequired("factor", factor());
        }

        public Term21 term21() {
            var element = getItem(0);
            if (!element.isPresent()) return null;
            return Term21.of(element);
        }

        public Factor factor() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return Factor.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = Term21.parse(parseTree, level + 1);
            result = result && Factor.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
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

        @Override
        protected void buildRule() {
            addChoice("isTokenTimes", isTokenTimes());
            addChoice("isTokenMatrixTimes", isTokenMatrixTimes());
            addChoice("isTokenDiv", isTokenDiv());
            addChoice("isTokenModulus", isTokenModulus());
            addChoice("isTokenFloorDiv", isTokenFloorDiv());
        }

        public boolean isTokenTimes() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public boolean isTokenMatrixTimes() {
            var element = getItem(1);
            return element.asBoolean();
        }

        public boolean isTokenDiv() {
            var element = getItem(2);
            return element.asBoolean();
        }

        public boolean isTokenModulus() {
            var element = getItem(3);
            return element.asBoolean();
        }

        public boolean isTokenFloorDiv() {
            var element = getItem(4);
            return element.asBoolean();
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral("*");
            result = result || parseTree.consumeTokenLiteral("@");
            result = result || parseTree.consumeTokenLiteral("/");
            result = result || parseTree.consumeTokenLiteral("%");
            result = result || parseTree.consumeTokenLiteral("//");

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}

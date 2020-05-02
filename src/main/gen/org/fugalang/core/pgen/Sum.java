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

    private List<Sum2> sum2List;

    @Override
    protected void buildRule() {
        addRequired("term", term());
        addRequired("sum2List", sum2List());
    }

    public Term term() {
        var element = getItem(0);
        if (!element.isPresent()) return null;
        return Term.of(element);
    }

    public List<Sum2> sum2List() {
        return sum2List;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = Term.parse(parseTree, level + 1);
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!Sum2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();

        parseTree.exit(level, marker, result);
        return result;
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

        @Override
        protected void buildRule() {
            addRequired("sum21", sum21());
            addRequired("term", term());
        }

        public Sum21 sum21() {
            var element = getItem(0);
            if (!element.isPresent()) return null;
            return Sum21.of(element);
        }

        public Term term() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return Term.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = Sum21.parse(parseTree, level + 1);
            result = result && Term.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
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

        @Override
        protected void buildRule() {
            addChoice("isTokenPlus", isTokenPlus());
            addChoice("isTokenMinus", isTokenMinus());
        }

        public boolean isTokenPlus() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public boolean isTokenMinus() {
            var element = getItem(1);
            return element.asBoolean();
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral("+");
            result = result || parseTree.consumeTokenLiteral("-");

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}

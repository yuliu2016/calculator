package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.ArrayList;
import java.util.Collections;
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
        addRequired(term());
        addRequired(sum2List());
    }

    public Term term() {
        var element = getItem(0);
        element.failIfAbsent(Term.RULE);
        return Term.of(element);
    }

    public List<Sum2> sum2List() {
        if (sum2List != null) {
            return sum2List;
        }
        List<Sum2> result = null;
        var element = getItem(1);
        for (var node : element.asCollection()) {
            if (result == null) result = new ArrayList<>();
            result.add(Sum2.of(node));
        }
        sum2List = result == null ? Collections.emptyList() : result;
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
            addRequired(sum21());
            addRequired(term());
        }

        public Sum21 sum21() {
            var element = getItem(0);
            element.failIfAbsent(Sum21.RULE);
            return Sum21.of(element);
        }

        public Term term() {
            var element = getItem(1);
            element.failIfAbsent(Term.RULE);
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
            addChoice(isTokenPlus());
            addChoice(isTokenMinus());
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

            result = parseTree.consumeToken("+");
            result = result || parseTree.consumeToken("-");

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}

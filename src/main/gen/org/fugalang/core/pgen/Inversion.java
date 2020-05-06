package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * inversion: 'not' 'inversion' | 'comparison'
 */
public final class Inversion extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("inversion", RuleType.Disjunction, true);

    public static Inversion of(ParseTreeNode node) {
        return new Inversion(node);
    }

    private Inversion(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addChoice(inversion1());
        addChoice(comparison());
    }

    public Inversion1 inversion1() {
        var element = getItem(0);
        element.failIfAbsent(Inversion1.RULE);
        return Inversion1.of(element);
    }

    public Inversion1 inversion1OrNull() {
        var element = getItem(0);
        if (!element.isPresent(Inversion1.RULE)) {
            return null;
        }
        return Inversion1.of(element);
    }

    public boolean hasInversion1() {
        var element = getItem(0);
        return element.isPresent(Inversion1.RULE);
    }

    public Comparison comparison() {
        var element = getItem(1);
        element.failIfAbsent(Comparison.RULE);
        return Comparison.of(element);
    }

    public Comparison comparisonOrNull() {
        var element = getItem(1);
        if (!element.isPresent(Comparison.RULE)) {
            return null;
        }
        return Comparison.of(element);
    }

    public boolean hasComparison() {
        var element = getItem(1);
        return element.isPresent(Comparison.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = Inversion1.parse(parseTree, level + 1);
        result = result || Comparison.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * 'not' 'inversion'
     */
    public static final class Inversion1 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("inversion:1", RuleType.Conjunction, false);

        public static Inversion1 of(ParseTreeNode node) {
            return new Inversion1(node);
        }

        private Inversion1(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired(isTokenNot(), "not");
            addRequired(inversion());
        }

        public boolean isTokenNot() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public Inversion inversion() {
            var element = getItem(1);
            element.failIfAbsent(Inversion.RULE);
            return Inversion.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("not");
            result = result && Inversion.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}

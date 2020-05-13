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

    public Inversion1 inversion1() {
        return Inversion1.of(getItem(0));
    }

    public boolean hasInversion1() {
        return hasItemOfRule(0, Inversion1.RULE);
    }

    public Comparison comparison() {
        return Comparison.of(getItem(1));
    }

    public boolean hasComparison() {
        return hasItemOfRule(1, Comparison.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
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

        public boolean isTokenNot() {
            return true;
        }

        public Inversion inversion() {
            return Inversion.of(getItem(1));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("not");
            result = result && Inversion.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}

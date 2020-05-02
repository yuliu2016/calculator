package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * inversion: 'not' 'inversion' | 'comparison'
 */
public final class Inversion extends DisjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("inversion", RuleType.Disjunction, true);

    private final Inversion1 inversion1;
    private final Comparison comparison;

    public Inversion(
            Inversion1 inversion1,
            Comparison comparison
    ) {
        this.inversion1 = inversion1;
        this.comparison = comparison;
    }

    @Override
    protected void buildRule() {
        addChoice("inversion1", inversion1());
        addChoice("comparison", comparison());
    }

    public Inversion1 inversion1() {
        return inversion1;
    }

    public boolean hasInversion1() {
        return inversion1() != null;
    }

    public Comparison comparison() {
        return comparison;
    }

    public boolean hasComparison() {
        return comparison() != null;
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
    public static final class Inversion1 extends ConjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("inversion:1", RuleType.Conjunction, false);

        private final boolean isTokenNot;
        private final Inversion inversion;

        public Inversion1(
                boolean isTokenNot,
                Inversion inversion
        ) {
            this.isTokenNot = isTokenNot;
            this.inversion = inversion;
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenNot", isTokenNot());
            addRequired("inversion", inversion());
        }

        public boolean isTokenNot() {
            return isTokenNot;
        }

        public Inversion inversion() {
            return inversion;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral("not");
            result = result && Inversion.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}

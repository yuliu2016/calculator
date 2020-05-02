package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * factor: ('+' | '-' | '~') 'factor' | 'power'
 */
public final class Factor extends DisjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("factor", RuleType.Disjunction, true);

    private final Factor1 factor1;
    private final Power power;

    public Factor(
            Factor1 factor1,
            Power power
    ) {
        this.factor1 = factor1;
        this.power = power;
    }

    @Override
    protected void buildRule() {
        addChoice("factor1", factor1());
        addChoice("power", power());
    }

    public Factor1 factor1() {
        return factor1;
    }

    public boolean hasFactor1() {
        return factor1() != null;
    }

    public Power power() {
        return power;
    }

    public boolean hasPower() {
        return power() != null;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = Factor1.parse(parseTree, level + 1);
        result = result || Power.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * ('+' | '-' | '~') 'factor'
     */
    public static final class Factor1 extends ConjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("factor:1", RuleType.Conjunction, false);

        private final Factor11 factor11;
        private final Factor factor;

        public Factor1(
                Factor11 factor11,
                Factor factor
        ) {
            this.factor11 = factor11;
            this.factor = factor;
        }

        @Override
        protected void buildRule() {
            addRequired("factor11", factor11());
            addRequired("factor", factor());
        }

        public Factor11 factor11() {
            return factor11;
        }

        public Factor factor() {
            return factor;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = Factor11.parse(parseTree, level + 1);
            result = result && Factor.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * '+' | '-' | '~'
     */
    public static final class Factor11 extends DisjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("factor:1:1", RuleType.Disjunction, false);

        private final boolean isTokenPlus;
        private final boolean isTokenMinus;
        private final boolean isTokenBitNot;

        public Factor11(
                boolean isTokenPlus,
                boolean isTokenMinus,
                boolean isTokenBitNot
        ) {
            this.isTokenPlus = isTokenPlus;
            this.isTokenMinus = isTokenMinus;
            this.isTokenBitNot = isTokenBitNot;
        }

        @Override
        protected void buildRule() {
            addChoice("isTokenPlus", isTokenPlus());
            addChoice("isTokenMinus", isTokenMinus());
            addChoice("isTokenBitNot", isTokenBitNot());
        }

        public boolean isTokenPlus() {
            return isTokenPlus;
        }

        public boolean isTokenMinus() {
            return isTokenMinus;
        }

        public boolean isTokenBitNot() {
            return isTokenBitNot;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral("+");
            result = result || parseTree.consumeTokenLiteral("-");
            result = result || parseTree.consumeTokenLiteral("~");

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}

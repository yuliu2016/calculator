package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * factor: ('+' | '-' | '~') 'factor' | 'power'
 */
public final class Factor extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("factor", RuleType.Disjunction, true);

    public static Factor of(ParseTreeNode node) {
        return new Factor(node);
    }

    private Factor(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addChoice(factor1());
        addChoice(power());
    }

    public Factor1 factor1() {
        var element = getItem(0);
        if (!element.isPresent(Factor1.RULE)) {
            return null;
        }
        return Factor1.of(element);
    }

    public boolean hasFactor1() {
        return factor1() != null;
    }

    public Power power() {
        var element = getItem(1);
        if (!element.isPresent(Power.RULE)) {
            return null;
        }
        return Power.of(element);
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
    public static final class Factor1 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("factor:1", RuleType.Conjunction, false);

        public static Factor1 of(ParseTreeNode node) {
            return new Factor1(node);
        }

        private Factor1(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired(factor11());
            addRequired(factor());
        }

        public Factor11 factor11() {
            var element = getItem(0);
            element.failIfAbsent(Factor11.RULE);
            return Factor11.of(element);
        }

        public Factor factor() {
            var element = getItem(1);
            element.failIfAbsent(Factor.RULE);
            return Factor.of(element);
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
    public static final class Factor11 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("factor:1:1", RuleType.Disjunction, false);

        public static Factor11 of(ParseTreeNode node) {
            return new Factor11(node);
        }

        private Factor11(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addChoice(isTokenPlus());
            addChoice(isTokenMinus());
            addChoice(isTokenBitNot());
        }

        public boolean isTokenPlus() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public boolean isTokenMinus() {
            var element = getItem(1);
            return element.asBoolean();
        }

        public boolean isTokenBitNot() {
            var element = getItem(2);
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
            result = result || parseTree.consumeToken("~");

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}

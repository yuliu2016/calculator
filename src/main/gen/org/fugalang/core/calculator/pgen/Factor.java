package org.fugalang.core.calculator.pgen;

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

    public Factor1 factor1() {
        return Factor1.of(getItem(0));
    }

    public boolean hasFactor1() {
        return hasItemOfRule(0, Factor1.RULE);
    }

    public Power power() {
        return Power.of(getItem(1));
    }

    public boolean hasPower() {
        return hasItemOfRule(1, Power.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        parseTree.enter(level, RULE);
        boolean result;

        result = Factor1.parse(parseTree, level + 1);
        result = result || Power.parse(parseTree, level + 1);

        parseTree.exit(result);
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

        public Factor11 factor11() {
            return Factor11.of(getItem(0));
        }

        public Factor factor() {
            return Factor.of(getItem(1));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            parseTree.enter(level, RULE);
            boolean result;

            result = Factor11.parse(parseTree, level + 1);
            result = result && Factor.parse(parseTree, level + 1);

            parseTree.exit(result);
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

        public boolean isTokenPlus() {
            return getBoolean(0);
        }

        public boolean isTokenMinus() {
            return getBoolean(1);
        }

        public boolean isTokenBitNot() {
            return getBoolean(2);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("+");
            result = result || parseTree.consumeToken("-");
            result = result || parseTree.consumeToken("~");

            parseTree.exit(result);
            return result;
        }
    }
}

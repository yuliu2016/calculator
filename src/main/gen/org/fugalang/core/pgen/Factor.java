package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * factor: ('+' | '-' | '~') 'factor' | 'power'
 */
public final class Factor extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("factor", RuleType.Disjunction);

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

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = Factor1.parse(t, lv + 1);
        r = r || Power.parse(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * ('+' | '-' | '~') 'factor'
     */
    public static final class Factor1 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("factor:1", RuleType.Conjunction);

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

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = Factor11.parse(t, lv + 1);
            r = r && Factor.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }

    /**
     * '+' | '-' | '~'
     */
    public static final class Factor11 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("factor:1:1", RuleType.Disjunction);

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

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consumeToken("+");
            r = r || t.consumeToken("-");
            r = r || t.consumeToken("~");
            t.exit(r);
            return r;
        }
    }
}

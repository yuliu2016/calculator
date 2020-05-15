package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * inversion: 'not' 'inversion' | 'comparison'
 */
public final class Inversion extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("inversion", RuleType.Disjunction);

    public static Inversion of(ParseTreeNode node) {
        return new Inversion(node);
    }

    private Inversion(ParseTreeNode node) {
        super(RULE, node);
    }

    public Inversion1 inversion1() {
        return Inversion1.of(get(0));
    }

    public boolean hasInversion1() {
        return has(0, Inversion1.RULE);
    }

    public Comparison comparison() {
        return Comparison.of(get(1));
    }

    public boolean hasComparison() {
        return has(1, Comparison.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = Inversion1.parse(t, lv + 1);
        r = r || Comparison.parse(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * 'not' 'inversion'
     */
    public static final class Inversion1 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("inversion:1", RuleType.Conjunction);

        public static Inversion1 of(ParseTreeNode node) {
            return new Inversion1(node);
        }

        private Inversion1(ParseTreeNode node) {
            super(RULE, node);
        }

        public Inversion inversion() {
            return Inversion.of(get(1));
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume("not");
            r = r && Inversion.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}

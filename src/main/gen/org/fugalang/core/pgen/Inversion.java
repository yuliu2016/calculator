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

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = Inversion1.parse(t, l + 1);
        r = r || Comparison.parse(t, l + 1);
        t.exit(r);
        return r;
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

        public Inversion inversion() {
            return Inversion.of(getItem(1));
        }

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = t.consumeToken("not");
            r = r && Inversion.parse(t, l + 1);
            t.exit(r);
            return r;
        }
    }
}

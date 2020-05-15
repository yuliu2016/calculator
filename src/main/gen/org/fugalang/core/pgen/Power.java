package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * power: 'atom_expr' ['**' 'factor']
 */
public final class Power extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("power", RuleType.Conjunction, true);

    public static Power of(ParseTreeNode node) {
        return new Power(node);
    }

    private Power(ParseTreeNode node) {
        super(RULE, node);
    }

    public AtomExpr atomExpr() {
        return AtomExpr.of(getItem(0));
    }

    public Power2 power2() {
        return Power2.of(getItem(1));
    }

    public boolean hasPower2() {
        return hasItemOfRule(1, Power2.RULE);
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = AtomExpr.parse(t, l + 1);
        if (r) Power2.parse(t, l + 1);
        t.exit(r);
        return r;
    }

    /**
     * '**' 'factor'
     */
    public static final class Power2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("power:2", RuleType.Conjunction, false);

        public static Power2 of(ParseTreeNode node) {
            return new Power2(node);
        }

        private Power2(ParseTreeNode node) {
            super(RULE, node);
        }

        public Factor factor() {
            return Factor.of(getItem(1));
        }

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = t.consumeToken("**");
            r = r && Factor.parse(t, l + 1);
            t.exit(r);
            return r;
        }
    }
}

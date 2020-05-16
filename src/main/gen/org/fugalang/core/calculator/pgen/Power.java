package org.fugalang.core.calculator.pgen;

import org.fugalang.core.parser.*;

/**
 * power: 'atom' ['**' 'factor']
 */
public final class Power extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("power", RuleType.Conjunction);

    public static Power of(ParseTreeNode node) {
        return new Power(node);
    }

    private Power(ParseTreeNode node) {
        super(RULE, node);
    }

    public Atom atom() {
        return Atom.of(get(0));
    }

    public Power2 factor() {
        return Power2.of(get(1));
    }

    public boolean hasFactor() {
        return has(1, Power2.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = Atom.parse(t, lv + 1);
        if (r) Power2.parse(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * '**' 'factor'
     */
    public static final class Power2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("power:2", RuleType.Conjunction);

        public static Power2 of(ParseTreeNode node) {
            return new Power2(node);
        }

        private Power2(ParseTreeNode node) {
            super(RULE, node);
        }

        public Factor factor() {
            return Factor.of(get(1));
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume("**");
            r = r && Factor.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}

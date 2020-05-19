package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * power: 'primary' ['**' 'factor']
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

    public Primary primary() {
        return get(0, Primary::of);
    }

    public Power2 factor() {
        return get(1, Power2::of);
    }

    public boolean hasFactor() {
        return has(1, Power2.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = Primary.parse(t, lv + 1);
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
            return get(1, Factor::of);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (t.recursionGuard(lv)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume("**");
            r = r && Factor.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}

package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * target: 'bitwise_or' | 'star_expr'
 */
public final class Target extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("target", RuleType.Disjunction);

    public static Target of(ParseTreeNode node) {
        return new Target(node);
    }

    private Target(ParseTreeNode node) {
        super(RULE, node);
    }

    public BitwiseOr bitwiseOr() {
        return BitwiseOr.of(get(0));
    }

    public boolean hasBitwiseOr() {
        return has(0, BitwiseOr.RULE);
    }

    public StarExpr starExpr() {
        return StarExpr.of(get(1));
    }

    public boolean hasStarExpr() {
        return has(1, StarExpr.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = BitwiseOr.parse(t, lv + 1);
        r = r || StarExpr.parse(t, lv + 1);
        t.exit(r);
        return r;
    }
}

package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * target: 'bitwise_or' | 'star_expr'
 */
public final class Target extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("target", RuleType.Disjunction, true);

    public static Target of(ParseTreeNode node) {
        return new Target(node);
    }

    private Target(ParseTreeNode node) {
        super(RULE, node);
    }

    public BitwiseOr bitwiseOr() {
        return BitwiseOr.of(getItem(0));
    }

    public boolean hasBitwiseOr() {
        return hasItemOfRule(0, BitwiseOr.RULE);
    }

    public StarExpr starExpr() {
        return StarExpr.of(getItem(1));
    }

    public boolean hasStarExpr() {
        return hasItemOfRule(1, StarExpr.RULE);
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = BitwiseOr.parse(t, l + 1);
        r = r || StarExpr.parse(t, l + 1);
        t.exit(r);
        return r;
    }
}

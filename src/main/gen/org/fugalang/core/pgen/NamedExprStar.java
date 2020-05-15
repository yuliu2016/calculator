package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * named_expr_star: 'star_expr' | 'named_expr'
 */
public final class NamedExprStar extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("named_expr_star", RuleType.Disjunction);

    public static NamedExprStar of(ParseTreeNode node) {
        return new NamedExprStar(node);
    }

    private NamedExprStar(ParseTreeNode node) {
        super(RULE, node);
    }

    public StarExpr starExpr() {
        return StarExpr.of(get(0));
    }

    public boolean hasStarExpr() {
        return has(0, StarExpr.RULE);
    }

    public NamedExpr namedExpr() {
        return NamedExpr.of(get(1));
    }

    public boolean hasNamedExpr() {
        return has(1, NamedExpr.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = StarExpr.parse(t, lv + 1);
        r = r || NamedExpr.parse(t, lv + 1);
        t.exit(r);
        return r;
    }
}

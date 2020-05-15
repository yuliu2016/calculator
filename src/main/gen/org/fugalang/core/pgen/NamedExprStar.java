package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * named_expr_star: 'star_expr' | 'named_expr'
 */
public final class NamedExprStar extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("named_expr_star", RuleType.Disjunction, true);

    public static NamedExprStar of(ParseTreeNode node) {
        return new NamedExprStar(node);
    }

    private NamedExprStar(ParseTreeNode node) {
        super(RULE, node);
    }

    public StarExpr starExpr() {
        return StarExpr.of(getItem(0));
    }

    public boolean hasStarExpr() {
        return hasItemOfRule(0, StarExpr.RULE);
    }

    public NamedExpr namedExpr() {
        return NamedExpr.of(getItem(1));
    }

    public boolean hasNamedExpr() {
        return hasItemOfRule(1, NamedExpr.RULE);
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = StarExpr.parse(t, l + 1);
        r = r || NamedExpr.parse(t, l + 1);
        t.exit(r);
        return r;
    }
}

package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * expr_or_star: 'star_expr' | 'expr'
 */
public final class ExprOrStar extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("expr_or_star", RuleType.Disjunction);

    public static ExprOrStar of(ParseTreeNode node) {
        return new ExprOrStar(node);
    }

    private ExprOrStar(ParseTreeNode node) {
        super(RULE, node);
    }

    public StarExpr starExpr() {
        return StarExpr.of(getItem(0));
    }

    public boolean hasStarExpr() {
        return hasItemOfRule(0, StarExpr.RULE);
    }

    public Expr expr() {
        return Expr.of(getItem(1));
    }

    public boolean hasExpr() {
        return hasItemOfRule(1, Expr.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = StarExpr.parse(t, lv + 1);
        r = r || Expr.parse(t, lv + 1);
        t.exit(r);
        return r;
    }
}

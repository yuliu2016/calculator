package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * expr_or_star: 'star_expr' | 'expr'
 */
public final class ExprOrStar extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("expr_or_star", RuleType.Disjunction, true);

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

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = StarExpr.parse(t, l + 1);
        r = r || Expr.parse(t, l + 1);
        t.exit(r);
        return r;
    }
}

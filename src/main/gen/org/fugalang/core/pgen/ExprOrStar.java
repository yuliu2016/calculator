package org.fugalang.core.pgen;

import org.fugalang.core.parser.DisjunctionRule;
import org.fugalang.core.parser.ParseTree;

/**
 * expr_or_star: 'expr' | 'star_expr'
 */
public final class ExprOrStar extends DisjunctionRule {
    public static final String RULE_NAME = "expr_or_star";

    private final Expr expr;
    private final StarExpr starExpr;

    public ExprOrStar(
            Expr expr,
            StarExpr starExpr
    ) {
        this.expr = expr;
        this.starExpr = starExpr;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addChoice("expr", expr);
        addChoice("starExpr", starExpr);
    }

    public Expr expr() {
        return expr;
    }

    public boolean hasExpr() {
        return expr() != null;
    }

    public StarExpr starExpr() {
        return starExpr;
    }

    public boolean hasStarExpr() {
        return starExpr() != null;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        boolean result;

        result = Expr.parse(parseTree, level + 1);
        result = result || StarExpr.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}

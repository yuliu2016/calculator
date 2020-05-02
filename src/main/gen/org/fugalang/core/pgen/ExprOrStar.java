package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * expr_or_star: 'expr' | 'star_expr'
 */
public final class ExprOrStar extends DisjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("expr_or_star", RuleType.Disjunction, true);

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
        addChoice("expr", expr());
        addChoice("starExpr", starExpr());
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
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = Expr.parse(parseTree, level + 1);
        result = result || StarExpr.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}

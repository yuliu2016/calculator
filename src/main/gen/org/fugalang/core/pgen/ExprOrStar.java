package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * expr_or_star: 'expr' | 'star_expr'
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

    @Override
    protected void buildRule() {
        addChoice(expr());
        addChoice(starExpr());
    }

    public Expr expr() {
        var element = getItem(0);
        if (!element.isPresent(Expr.RULE)) {
            return null;
        }
        return Expr.of(element);
    }

    public boolean hasExpr() {
        return expr() != null;
    }

    public StarExpr starExpr() {
        var element = getItem(1);
        if (!element.isPresent(StarExpr.RULE)) {
            return null;
        }
        return StarExpr.of(element);
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

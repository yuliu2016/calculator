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
        return get(0, StarExpr::of);
    }

    public boolean hasStarExpr() {
        return has(0, StarExpr.RULE);
    }

    public NamedExpr namedExpr() {
        return get(1, NamedExpr::of);
    }

    public boolean hasNamedExpr() {
        return has(1, NamedExpr.RULE);
    }
}

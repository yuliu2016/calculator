package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

/**
 * named_expr_star: 'star_expr' | 'named_expr'
 */
public final class NamedExprStar extends NodeWrapper {

    public NamedExprStar(ParseTreeNode node) {
        super(ParserRules.NAMED_EXPR_STAR, node);
    }

    public StarExpr starExpr() {
        return get(0, StarExpr::new);
    }

    public boolean hasStarExpr() {
        return has(0, ParserRules.STAR_EXPR);
    }

    public NamedExpr namedExpr() {
        return get(1, NamedExpr::new);
    }

    public boolean hasNamedExpr() {
        return has(1, ParserRules.NAMED_EXPR);
    }
}

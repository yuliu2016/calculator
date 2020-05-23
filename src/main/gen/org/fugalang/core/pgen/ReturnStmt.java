package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * return_stmt: 'return' ['exprlist_star']
 */
public final class ReturnStmt extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("return_stmt", RuleType.Conjunction);

    public static ReturnStmt of(ParseTreeNode node) {
        return new ReturnStmt(node);
    }

    private ReturnStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    public ExprlistStar exprlistStar() {
        return get(1, ExprlistStar::of);
    }

    public boolean hasExprlistStar() {
        return has(1, ExprlistStar.RULE);
    }
}

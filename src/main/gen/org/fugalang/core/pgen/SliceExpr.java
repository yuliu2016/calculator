package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

/**
 * slice_expr: ':' ['expr']
 */
public final class SliceExpr extends NodeWrapper {

    public SliceExpr(ParseTreeNode node) {
        super(ParserRules.SLICE_EXPR, node);
    }

    public Expr expr() {
        return get(1, Expr::new);
    }

    public boolean hasExpr() {
        return has(1, ParserRules.EXPR);
    }
}

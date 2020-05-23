package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * elif_stmt: 'elif' 'named_expr' 'suite'
 */
public final class ElifStmt extends NodeWrapper {

    public ElifStmt(ParseTreeNode node) {
        super(node);
    }

    public NamedExpr namedExpr() {
        return get(1, NamedExpr.class);
    }

    public Suite suite() {
        return get(2, Suite.class);
    }
}

package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * while_stmt: 'while' 'named_expr' 'suite' ['else_suite']
 */
public final class WhileStmt extends NodeWrapper {

    public WhileStmt(ParseTreeNode node) {
        super(node);
    }

    public NamedExpr namedExpr() {
        return get(1, NamedExpr.class);
    }

    public Suite suite() {
        return get(2, Suite.class);
    }

    public ElseSuite elseSuite() {
        return get(3, ElseSuite.class);
    }

    public boolean hasElseSuite() {
        return has(3);
    }
}

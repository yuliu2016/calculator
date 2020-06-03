package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * with_stmt: 'with' ','.expr_as_name+ suite
 */
public final class WithStmt extends NodeWrapper {

    public WithStmt(ParseTreeNode node) {
        super(node);
    }

    public List<ExprAsName> exprAsNames() {
        return getList(1, ExprAsName::new);
    }

    public Suite suite() {
        return new Suite(get(2));
    }
}

package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * with_stmt: 'with' expr_as_name (',' expr_as_name)* suite
 */
public final class WithStmt extends NodeWrapper {

    public WithStmt(ParseTreeNode node) {
        super(node);
    }

    public ExprAsName exprAsName() {
        return get(1, ExprAsName.class);
    }

    public List<WithStmt3> commaExprAsNames() {
        return getList(2, WithStmt3.class);
    }

    public Suite suite() {
        return get(3, Suite.class);
    }

    /**
     * ',' expr_as_name
     */
    public static final class WithStmt3 extends NodeWrapper {

        public WithStmt3(ParseTreeNode node) {
            super(node);
        }

        public ExprAsName exprAsName() {
            return get(1, ExprAsName.class);
        }
    }
}

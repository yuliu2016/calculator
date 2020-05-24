package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * simple_stmt: small_stmt (';' small_stmt)* [';']
 */
public final class SimpleStmt extends NodeWrapper {

    public SimpleStmt(ParseTreeNode node) {
        super(node);
    }

    public SmallStmt smallStmt() {
        return get(0, SmallStmt.class);
    }

    public List<SimpleStmt2> semicolonSmallStmts() {
        return getList(1, SimpleStmt2.class);
    }

    public boolean isSemicolon() {
        return is(2);
    }

    /**
     * ';' small_stmt
     */
    public static final class SimpleStmt2 extends NodeWrapper {

        public SimpleStmt2(ParseTreeNode node) {
            super(node);
        }

        public SmallStmt smallStmt() {
            return get(1, SmallStmt.class);
        }
    }
}

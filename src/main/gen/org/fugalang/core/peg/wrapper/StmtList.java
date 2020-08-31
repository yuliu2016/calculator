package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * stmt_list:
 * *   | stmt+
 */
public final class StmtList extends NodeWrapper {

    public StmtList(ParseTreeNode node) {
        super(node);
    }

    public List<Stmt> stmts() {
        return getList(0, Stmt::new);
    }
}

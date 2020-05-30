package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * simple_stmt: ';'.small_stmt+ [';']
 */
public final class SimpleStmt extends NodeWrapper {

    public SimpleStmt(ParseTreeNode node) {
        super(node);
    }

    public List<SmallStmt> smallStmts() {
        return getList(0, SmallStmt.class);
    }

    public boolean isSemicolon() {
        return is(1);
    }
}

package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * del_stmt: 'del' targetlist
 */
public final class DelStmt extends NodeWrapper {

    public DelStmt(ParseTreeNode node) {
        super(node);
    }

    public Targetlist targetlist() {
        return new Targetlist(get(1));
    }
}

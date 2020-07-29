package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * nonlocal_stmt:
 * *   | 'nonlocal' name_list
 */
public final class NonlocalStmt extends NodeWrapper {

    public NonlocalStmt(ParseTreeNode node) {
        super(node);
    }

    public NameList nameList() {
        return new NameList(get(1));
    }
}

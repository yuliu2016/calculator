package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.FugaRules;

/**
 * del_stmt: 'del' 'targetlist'
 */
public final class DelStmt extends NodeWrapper {

    public DelStmt(ParseTreeNode node) {
        super(FugaRules.DEL_STMT, node);
    }

    public Targetlist targetlist() {
        return get(1, Targetlist.class);
    }
}

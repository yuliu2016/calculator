package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.FugaRules;

/**
 * for_stmt: 'for' 'targetlist' 'in' 'exprlist' 'suite' ['else_suite']
 */
public final class ForStmt extends NodeWrapper {

    public ForStmt(ParseTreeNode node) {
        super(FugaRules.FOR_STMT, node);
    }

    public Targetlist targetlist() {
        return get(1, Targetlist.class);
    }

    public Exprlist exprlist() {
        return get(3, Exprlist.class);
    }

    public Suite suite() {
        return get(4, Suite.class);
    }

    public ElseSuite elseSuite() {
        return get(5, ElseSuite.class);
    }

    public boolean hasElseSuite() {
        return has(5);
    }
}

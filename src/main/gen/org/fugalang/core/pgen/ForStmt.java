package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

/**
 * for_stmt: 'for' 'targetlist' 'in' 'exprlist' 'suite' ['else_suite']
 */
public final class ForStmt extends NodeWrapper {

    public ForStmt(ParseTreeNode node) {
        super(ParserRules.FOR_STMT, node);
    }

    public Targetlist targetlist() {
        return get(1, Targetlist::new);
    }

    public Exprlist exprlist() {
        return get(3, Exprlist::new);
    }

    public Suite suite() {
        return get(4, Suite::new);
    }

    public ElseSuite elseSuite() {
        return get(5, ElseSuite::new);
    }

    public boolean hasElseSuite() {
        return has(5);
    }
}

package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * for_stmt:
 * *   | 'for' targetlist 'in' exprlist suite [else_suite]
 */
public final class ForStmt extends NodeWrapper {

    public ForStmt(ParseTreeNode node) {
        super(node);
    }

    public Targetlist targetlist() {
        return new Targetlist(get(1));
    }

    public Exprlist exprlist() {
        return new Exprlist(get(3));
    }

    public Suite suite() {
        return new Suite(get(4));
    }

    public ElseSuite elseSuite() {
        return new ElseSuite(get(5));
    }

    public boolean hasElseSuite() {
        return has(5);
    }
}

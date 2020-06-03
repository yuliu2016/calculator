package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * if_stmt: 'if' named_expr suite elif_stmt* [else_suite]
 */
public final class IfStmt extends NodeWrapper {

    public IfStmt(ParseTreeNode node) {
        super(node);
    }

    public NamedExpr namedExpr() {
        return new NamedExpr(get(1));
    }

    public Suite suite() {
        return new Suite(get(2));
    }

    public List<ElifStmt> elifStmts() {
        return getList(3, ElifStmt::new);
    }

    public ElseSuite elseSuite() {
        return new ElseSuite(get(4));
    }

    public boolean hasElseSuite() {
        return has(4);
    }
}

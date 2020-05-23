package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.FugaRules;

import java.util.List;

/**
 * if_stmt: 'if' 'named_expr' 'suite' 'elif_stmt'* ['else_suite']
 */
public final class IfStmt extends NodeWrapper {

    public IfStmt(ParseTreeNode node) {
        super(FugaRules.IF_STMT, node);
    }

    public NamedExpr namedExpr() {
        return get(1, NamedExpr.class);
    }

    public Suite suite() {
        return get(2, Suite.class);
    }

    public List<ElifStmt> elifStmts() {
        return getList(3, ElifStmt.class);
    }

    public ElseSuite elseSuite() {
        return get(4, ElseSuite.class);
    }

    public boolean hasElseSuite() {
        return has(4);
    }
}

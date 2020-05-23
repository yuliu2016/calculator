package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

/**
 * while_stmt: 'while' 'named_expr' 'suite' ['else_suite']
 */
public final class WhileStmt extends NodeWrapper {

    public WhileStmt(ParseTreeNode node) {
        super(ParserRules.WHILE_STMT, node);
    }

    public NamedExpr namedExpr() {
        return get(1, NamedExpr::new);
    }

    public Suite suite() {
        return get(2, Suite::new);
    }

    public ElseSuite elseSuite() {
        return get(3, ElseSuite::new);
    }

    public boolean hasElseSuite() {
        return has(3, ParserRules.ELSE_SUITE);
    }
}

package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

/**
 * elif_stmt: 'elif' 'named_expr' 'suite'
 */
public final class ElifStmt extends NodeWrapper {

    public ElifStmt(ParseTreeNode node) {
        super(ParserRules.ELIF_STMT, node);
    }

    public NamedExpr namedExpr() {
        return get(1, NamedExpr::new);
    }

    public Suite suite() {
        return get(2, Suite::new);
    }
}

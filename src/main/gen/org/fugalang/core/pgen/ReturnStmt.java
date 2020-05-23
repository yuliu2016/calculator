package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

/**
 * return_stmt: 'return' ['exprlist_star']
 */
public final class ReturnStmt extends NodeWrapper {

    public ReturnStmt(ParseTreeNode node) {
        super(ParserRules.RETURN_STMT, node);
    }

    public ExprlistStar exprlistStar() {
        return get(1, ExprlistStar::new);
    }

    public boolean hasExprlistStar() {
        return has(1);
    }
}

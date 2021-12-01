package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * result_clause:
 * *   | '{' result_expr '}'
 */
public final class ResultClause extends NodeWrapper {

    public ResultClause(ParseTreeNode node) {
        super(node);
    }

    public ResultExpr resultExpr() {
        return new ResultExpr(get(1));
    }
}

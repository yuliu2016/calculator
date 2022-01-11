package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * result_expr:
 * *   | expr_call
 * *   | NAME
 * *   | STRING
 */
public final class ResultExpr extends NodeWrapper {

    public ResultExpr(ParseTreeNode node) {
        super(node);
    }

    public ExprCall exprCall() {
        return new ExprCall(get(0));
    }

    public boolean hasExprCall() {
        return has(0);
    }

    public String name() {
        return get(1, TokenType.NAME);
    }

    public boolean hasName() {
        return has(1);
    }

    public String string() {
        return get(2, TokenType.STRING);
    }

    public boolean hasString() {
        return has(2);
    }
}

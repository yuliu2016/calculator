package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * result_expr:
 * *   | NAME
 * *   | STRING
 */
public final class ResultExpr extends NodeWrapper {

    public ResultExpr(ParseTreeNode node) {
        super(node);
    }

    public String name() {
        return get(0, TokenType.NAME);
    }

    public boolean hasName() {
        return has(0);
    }

    public String string() {
        return get(1, TokenType.STRING);
    }

    public boolean hasString() {
        return has(1);
    }
}

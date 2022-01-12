package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * result_expr:
 * *   | [NEWLINE] '{' expression '}'
 */
public final class ResultExpr extends NodeWrapper {

    public ResultExpr(ParseTreeNode node) {
        super(node);
    }

    public String newline() {
        return get(0, TokenType.NEWLINE);
    }

    public boolean hasNewline() {
        return has(0);
    }

    public Expression expression() {
        return new Expression(get(2));
    }
}

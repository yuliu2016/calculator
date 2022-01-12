package org.fugalang.grammar.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * expr_arg:
 * *   | '%' NAME
 * *   | NUMBER
 * *   | expr_call
 * *   | expr_name
 */
public final class ExprArg extends NodeWrapper {

    public ExprArg(ParseTreeNode node) {
        super(node);
    }

    public ExprArg1 modulusName() {
        return new ExprArg1(get(0));
    }

    public boolean hasModulusName() {
        return has(0);
    }

    public String number() {
        return get(1, TokenType.NUMBER);
    }

    public boolean hasNumber() {
        return has(1);
    }

    public ExprCall exprCall() {
        return new ExprCall(get(2));
    }

    public boolean hasExprCall() {
        return has(2);
    }

    public ExprName exprName() {
        return new ExprName(get(3));
    }

    public boolean hasExprName() {
        return has(3);
    }

    /**
     * '%' NAME
     */
    public static final class ExprArg1 extends NodeWrapper {

        public ExprArg1(ParseTreeNode node) {
            super(node);
        }

        public String name() {
            return get(1, TokenType.NAME);
        }
    }
}

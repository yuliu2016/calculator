package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * simple_arg: NAME ['=' expr]
 */
public final class SimpleArg extends NodeWrapper {

    public SimpleArg(ParseTreeNode node) {
        super(node);
    }

    public String name() {
        return get(0, TokenType.NAME);
    }

    public SimpleArg2 assignExpr() {
        return new SimpleArg2(get(1));
    }

    public boolean hasAssignExpr() {
        return has(1);
    }

    /**
     * '=' expr
     */
    public static final class SimpleArg2 extends NodeWrapper {

        public SimpleArg2(ParseTreeNode node) {
            super(node);
        }

        public Expr expr() {
            return new Expr(get(1));
        }
    }
}

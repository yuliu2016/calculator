package org.fugalang.core.pgen;

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
        return get(1, SimpleArg2.class);
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
            return get(1, Expr.class);
        }
    }
}

package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * default_arg: typed_arg ['=' expr]
 */
public final class DefaultArg extends NodeWrapper {

    public DefaultArg(ParseTreeNode node) {
        super(node);
    }

    public TypedArg typedArg() {
        return new TypedArg(get(0));
    }

    public DefaultArg2 assignExpr() {
        return new DefaultArg2(get(1));
    }

    public boolean hasAssignExpr() {
        return has(1);
    }

    /**
     * '=' expr
     */
    public static final class DefaultArg2 extends NodeWrapper {

        public DefaultArg2(ParseTreeNode node) {
            super(node);
        }

        public Expr expr() {
            return new Expr(get(1));
        }
    }
}

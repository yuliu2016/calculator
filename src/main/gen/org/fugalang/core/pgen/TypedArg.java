package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * typed_arg: NAME [':' expr]
 */
public final class TypedArg extends NodeWrapper {

    public TypedArg(ParseTreeNode node) {
        super(node);
    }

    public String name() {
        return get(0, TokenType.NAME);
    }

    public TypedArg2 expr() {
        return get(1, TypedArg2.class);
    }

    public boolean hasExpr() {
        return has(1);
    }

    /**
     * ':' expr
     */
    public static final class TypedArg2 extends NodeWrapper {

        public TypedArg2(ParseTreeNode node) {
            super(node);
        }

        public Expr expr() {
            return get(1, Expr.class);
        }
    }
}

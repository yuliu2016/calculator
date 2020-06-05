package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * named_expr: NAME ':=' expr | expr
 */
public final class NamedExpr extends NodeWrapper {

    public NamedExpr(ParseTreeNode node) {
        super(node);
    }

    public NamedExpr1 nameAsgnExprExpr() {
        return new NamedExpr1(get(0));
    }

    public boolean hasNameAsgnExprExpr() {
        return has(0);
    }

    public Expr expr() {
        return new Expr(get(1));
    }

    public boolean hasExpr() {
        return has(1);
    }

    /**
     * NAME ':=' expr
     */
    public static final class NamedExpr1 extends NodeWrapper {

        public NamedExpr1(ParseTreeNode node) {
            super(node);
        }

        public String name() {
            return get(0, TokenType.NAME);
        }

        public Expr expr() {
            return new Expr(get(2));
        }
    }
}

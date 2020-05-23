package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * argument: NAME ':=' expr | NAME '=' expr | '**' expr | '*' expr | expr
 */
public final class Argument extends NodeWrapper {

    public Argument(ParseTreeNode node) {
        super(node);
    }

    public Argument1 nameExpr() {
        return get(0, Argument1.class);
    }

    public boolean hasNameExpr() {
        return has(0);
    }

    public Argument2 nameExpr1() {
        return get(1, Argument2.class);
    }

    public boolean hasNameExpr1() {
        return has(1);
    }

    public Argument3 expr() {
        return get(2, Argument3.class);
    }

    public boolean hasExpr() {
        return has(2);
    }

    public Argument4 expr1() {
        return get(3, Argument4.class);
    }

    public boolean hasExpr1() {
        return has(3);
    }

    public Expr expr2() {
        return get(4, Expr.class);
    }

    public boolean hasExpr2() {
        return has(4);
    }

    /**
     * NAME ':=' expr
     */
    public static final class Argument1 extends NodeWrapper {

        public Argument1(ParseTreeNode node) {
            super(node);
        }

        public String name() {
            return get(0, TokenType.NAME);
        }

        public Expr expr() {
            return get(2, Expr.class);
        }
    }

    /**
     * NAME '=' expr
     */
    public static final class Argument2 extends NodeWrapper {

        public Argument2(ParseTreeNode node) {
            super(node);
        }

        public String name() {
            return get(0, TokenType.NAME);
        }

        public Expr expr() {
            return get(2, Expr.class);
        }
    }

    /**
     * '**' expr
     */
    public static final class Argument3 extends NodeWrapper {

        public Argument3(ParseTreeNode node) {
            super(node);
        }

        public Expr expr() {
            return get(1, Expr.class);
        }
    }

    /**
     * '*' expr
     */
    public static final class Argument4 extends NodeWrapper {

        public Argument4(ParseTreeNode node) {
            super(node);
        }

        public Expr expr() {
            return get(1, Expr.class);
        }
    }
}

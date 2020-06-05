package org.fugalang.core.peg.wrapper;

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

    public Argument1 nameAsgnExprExpr() {
        return new Argument1(get(0));
    }

    public boolean hasNameAsgnExprExpr() {
        return has(0);
    }

    public Argument2 nameAssignExpr() {
        return new Argument2(get(1));
    }

    public boolean hasNameAssignExpr() {
        return has(1);
    }

    public Argument3 powerExpr() {
        return new Argument3(get(2));
    }

    public boolean hasPowerExpr() {
        return has(2);
    }

    public Argument4 timesExpr() {
        return new Argument4(get(3));
    }

    public boolean hasTimesExpr() {
        return has(3);
    }

    public Expr expr() {
        return new Expr(get(4));
    }

    public boolean hasExpr() {
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
            return new Expr(get(2));
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
            return new Expr(get(2));
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
            return new Expr(get(1));
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
            return new Expr(get(1));
        }
    }
}

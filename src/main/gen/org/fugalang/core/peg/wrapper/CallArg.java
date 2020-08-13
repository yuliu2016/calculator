package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * call_arg:
 * *   | NAME ':=' expr
 * *   | NAME '=' expr
 * *   | '**' expr
 * *   | '*' expr
 * *   | expr
 */
public final class CallArg extends NodeWrapper {

    public CallArg(ParseTreeNode node) {
        super(node);
    }

    public CallArg1 nameAsgnExprExpr() {
        return new CallArg1(get(0));
    }

    public boolean hasNameAsgnExprExpr() {
        return has(0);
    }

    public CallArg2 nameAssignExpr() {
        return new CallArg2(get(1));
    }

    public boolean hasNameAssignExpr() {
        return has(1);
    }

    public CallArg3 powerExpr() {
        return new CallArg3(get(2));
    }

    public boolean hasPowerExpr() {
        return has(2);
    }

    public CallArg4 timesExpr() {
        return new CallArg4(get(3));
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
    public static final class CallArg1 extends NodeWrapper {

        public CallArg1(ParseTreeNode node) {
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
    public static final class CallArg2 extends NodeWrapper {

        public CallArg2(ParseTreeNode node) {
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
    public static final class CallArg3 extends NodeWrapper {

        public CallArg3(ParseTreeNode node) {
            super(node);
        }

        public Expr expr() {
            return new Expr(get(1));
        }
    }

    /**
     * '*' expr
     */
    public static final class CallArg4 extends NodeWrapper {

        public CallArg4(ParseTreeNode node) {
            super(node);
        }

        public Expr expr() {
            return new Expr(get(1));
        }
    }
}

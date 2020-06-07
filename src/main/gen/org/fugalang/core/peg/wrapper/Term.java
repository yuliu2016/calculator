package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * term: 
 * | term '*' pipe_expr
 * | term '/' pipe_expr
 * | term '%' pipe_expr
 * | term '//' pipe_expr
 * | term '@' pipe_expr
 * | pipe_expr
 */
public final class Term extends NodeWrapper {

    public Term(ParseTreeNode node) {
        super(node);
    }

    public Term1 termTimesPipeExpr() {
        return new Term1(get(0));
    }

    public boolean hasTermTimesPipeExpr() {
        return has(0);
    }

    public Term2 termDivPipeExpr() {
        return new Term2(get(1));
    }

    public boolean hasTermDivPipeExpr() {
        return has(1);
    }

    public Term3 termModulusPipeExpr() {
        return new Term3(get(2));
    }

    public boolean hasTermModulusPipeExpr() {
        return has(2);
    }

    public Term4 termFloorDivPipeExpr() {
        return new Term4(get(3));
    }

    public boolean hasTermFloorDivPipeExpr() {
        return has(3);
    }

    public Term5 termMatrixTimesPipeExpr() {
        return new Term5(get(4));
    }

    public boolean hasTermMatrixTimesPipeExpr() {
        return has(4);
    }

    public PipeExpr pipeExpr() {
        return new PipeExpr(get(5));
    }

    public boolean hasPipeExpr() {
        return has(5);
    }

    /**
     * term '*' pipe_expr
     */
    public static final class Term1 extends NodeWrapper {

        public Term1(ParseTreeNode node) {
            super(node);
        }

        public Term term() {
            return new Term(get(0));
        }

        public PipeExpr pipeExpr() {
            return new PipeExpr(get(2));
        }
    }

    /**
     * term '/' pipe_expr
     */
    public static final class Term2 extends NodeWrapper {

        public Term2(ParseTreeNode node) {
            super(node);
        }

        public Term term() {
            return new Term(get(0));
        }

        public PipeExpr pipeExpr() {
            return new PipeExpr(get(2));
        }
    }

    /**
     * term '%' pipe_expr
     */
    public static final class Term3 extends NodeWrapper {

        public Term3(ParseTreeNode node) {
            super(node);
        }

        public Term term() {
            return new Term(get(0));
        }

        public PipeExpr pipeExpr() {
            return new PipeExpr(get(2));
        }
    }

    /**
     * term '//' pipe_expr
     */
    public static final class Term4 extends NodeWrapper {

        public Term4(ParseTreeNode node) {
            super(node);
        }

        public Term term() {
            return new Term(get(0));
        }

        public PipeExpr pipeExpr() {
            return new PipeExpr(get(2));
        }
    }

    /**
     * term '@' pipe_expr
     */
    public static final class Term5 extends NodeWrapper {

        public Term5(ParseTreeNode node) {
            super(node);
        }

        public Term term() {
            return new Term(get(0));
        }

        public PipeExpr pipeExpr() {
            return new PipeExpr(get(2));
        }
    }
}

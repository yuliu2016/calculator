package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * term: term '*' pipe | term '/' pipe | term '%' pipe | term '//' pipe | term '@' pipe | pipe
 */
public final class Term extends NodeWrapper {

    public Term(ParseTreeNode node) {
        super(node);
    }

    public Term1 termTimesPipe() {
        return new Term1(get(0));
    }

    public boolean hasTermTimesPipe() {
        return has(0);
    }

    public Term2 termDivPipe() {
        return new Term2(get(1));
    }

    public boolean hasTermDivPipe() {
        return has(1);
    }

    public Term3 termModulusPipe() {
        return new Term3(get(2));
    }

    public boolean hasTermModulusPipe() {
        return has(2);
    }

    public Term4 termFloorDivPipe() {
        return new Term4(get(3));
    }

    public boolean hasTermFloorDivPipe() {
        return has(3);
    }

    public Term5 termMatrixTimesPipe() {
        return new Term5(get(4));
    }

    public boolean hasTermMatrixTimesPipe() {
        return has(4);
    }

    public Pipe pipe() {
        return new Pipe(get(5));
    }

    public boolean hasPipe() {
        return has(5);
    }

    /**
     * term '*' pipe
     */
    public static final class Term1 extends NodeWrapper {

        public Term1(ParseTreeNode node) {
            super(node);
        }

        public Term term() {
            return new Term(get(0));
        }

        public Pipe pipe() {
            return new Pipe(get(2));
        }
    }

    /**
     * term '/' pipe
     */
    public static final class Term2 extends NodeWrapper {

        public Term2(ParseTreeNode node) {
            super(node);
        }

        public Term term() {
            return new Term(get(0));
        }

        public Pipe pipe() {
            return new Pipe(get(2));
        }
    }

    /**
     * term '%' pipe
     */
    public static final class Term3 extends NodeWrapper {

        public Term3(ParseTreeNode node) {
            super(node);
        }

        public Term term() {
            return new Term(get(0));
        }

        public Pipe pipe() {
            return new Pipe(get(2));
        }
    }

    /**
     * term '//' pipe
     */
    public static final class Term4 extends NodeWrapper {

        public Term4(ParseTreeNode node) {
            super(node);
        }

        public Term term() {
            return new Term(get(0));
        }

        public Pipe pipe() {
            return new Pipe(get(2));
        }
    }

    /**
     * term '@' pipe
     */
    public static final class Term5 extends NodeWrapper {

        public Term5(ParseTreeNode node) {
            super(node);
        }

        public Term term() {
            return new Term(get(0));
        }

        public Pipe pipe() {
            return new Pipe(get(2));
        }
    }
}

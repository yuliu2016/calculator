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
        return get(0, Term1.class);
    }

    public boolean hasTermTimesPipe() {
        return has(0);
    }

    public Term2 termDivPipe() {
        return get(1, Term2.class);
    }

    public boolean hasTermDivPipe() {
        return has(1);
    }

    public Term3 termModulusPipe() {
        return get(2, Term3.class);
    }

    public boolean hasTermModulusPipe() {
        return has(2);
    }

    public Term4 termFloorDivPipe() {
        return get(3, Term4.class);
    }

    public boolean hasTermFloorDivPipe() {
        return has(3);
    }

    public Term5 termMatrixTimesPipe() {
        return get(4, Term5.class);
    }

    public boolean hasTermMatrixTimesPipe() {
        return has(4);
    }

    public Pipe pipe() {
        return get(5, Pipe.class);
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
            return get(0, Term.class);
        }

        public Pipe pipe() {
            return get(2, Pipe.class);
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
            return get(0, Term.class);
        }

        public Pipe pipe() {
            return get(2, Pipe.class);
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
            return get(0, Term.class);
        }

        public Pipe pipe() {
            return get(2, Pipe.class);
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
            return get(0, Term.class);
        }

        public Pipe pipe() {
            return get(2, Pipe.class);
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
            return get(0, Term.class);
        }

        public Pipe pipe() {
            return get(2, Pipe.class);
        }
    }
}

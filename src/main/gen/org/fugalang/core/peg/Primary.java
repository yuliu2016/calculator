package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * primary: primary '.' NAME | primary parameters | primary subscript | primary block_suite | atom
 */
public final class Primary extends NodeWrapper {

    public Primary(ParseTreeNode node) {
        super(node);
    }

    public Primary1 primaryDotName() {
        return get(0, Primary1.class);
    }

    public boolean hasPrimaryDotName() {
        return has(0);
    }

    public Primary2 primaryParameters() {
        return get(1, Primary2.class);
    }

    public boolean hasPrimaryParameters() {
        return has(1);
    }

    public Primary3 primarySubscript() {
        return get(2, Primary3.class);
    }

    public boolean hasPrimarySubscript() {
        return has(2);
    }

    public Primary4 primaryBlockSuite() {
        return get(3, Primary4.class);
    }

    public boolean hasPrimaryBlockSuite() {
        return has(3);
    }

    public Atom atom() {
        return get(4, Atom.class);
    }

    public boolean hasAtom() {
        return has(4);
    }

    /**
     * primary '.' NAME
     */
    public static final class Primary1 extends NodeWrapper {

        public Primary1(ParseTreeNode node) {
            super(node);
        }

        public Primary primary() {
            return get(0, Primary.class);
        }

        public String name() {
            return get(2, TokenType.NAME);
        }
    }

    /**
     * primary parameters
     */
    public static final class Primary2 extends NodeWrapper {

        public Primary2(ParseTreeNode node) {
            super(node);
        }

        public Primary primary() {
            return get(0, Primary.class);
        }

        public Parameters parameters() {
            return get(1, Parameters.class);
        }
    }

    /**
     * primary subscript
     */
    public static final class Primary3 extends NodeWrapper {

        public Primary3(ParseTreeNode node) {
            super(node);
        }

        public Primary primary() {
            return get(0, Primary.class);
        }

        public Subscript subscript() {
            return get(1, Subscript.class);
        }
    }

    /**
     * primary block_suite
     */
    public static final class Primary4 extends NodeWrapper {

        public Primary4(ParseTreeNode node) {
            super(node);
        }

        public Primary primary() {
            return get(0, Primary.class);
        }

        public BlockSuite blockSuite() {
            return get(1, BlockSuite.class);
        }
    }
}

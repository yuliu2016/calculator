package org.fugalang.core.peg.wrapper;

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
        return new Primary1(get(0));
    }

    public boolean hasPrimaryDotName() {
        return has(0);
    }

    public Primary2 primaryParameters() {
        return new Primary2(get(1));
    }

    public boolean hasPrimaryParameters() {
        return has(1);
    }

    public Primary3 primarySubscript() {
        return new Primary3(get(2));
    }

    public boolean hasPrimarySubscript() {
        return has(2);
    }

    public Primary4 primaryBlockSuite() {
        return new Primary4(get(3));
    }

    public boolean hasPrimaryBlockSuite() {
        return has(3);
    }

    public Atom atom() {
        return new Atom(get(4));
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
            return new Primary(get(0));
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
            return new Primary(get(0));
        }

        public Parameters parameters() {
            return new Parameters(get(1));
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
            return new Primary(get(0));
        }

        public Subscript subscript() {
            return new Subscript(get(1));
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
            return new Primary(get(0));
        }

        public BlockSuite blockSuite() {
            return new BlockSuite(get(1));
        }
    }
}

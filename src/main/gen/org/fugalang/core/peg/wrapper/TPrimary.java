package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * t_primary:
 * *   | t_primary '.' NAME &t_lookahead
 * *   | t_primary parameters &t_lookahead
 * *   | t_primary subscript &t_lookahead
 * *   | atom &t_lookahead
 */
public final class TPrimary extends NodeWrapper {

    public TPrimary(ParseTreeNode node) {
        super(node);
    }

    public TPrimary1 tPrimary1() {
        return new TPrimary1(get(0));
    }

    public boolean hasTPrimary1() {
        return has(0);
    }

    public TPrimary2 tPrimaryParametersTLookahead() {
        return new TPrimary2(get(1));
    }

    public boolean hasTPrimaryParametersTLookahead() {
        return has(1);
    }

    public TPrimary3 tPrimarySubscriptTLookahead() {
        return new TPrimary3(get(2));
    }

    public boolean hasTPrimarySubscriptTLookahead() {
        return has(2);
    }

    public TPrimary4 atomTLookahead() {
        return new TPrimary4(get(3));
    }

    public boolean hasAtomTLookahead() {
        return has(3);
    }

    /**
     * t_primary '.' NAME &t_lookahead
     */
    public static final class TPrimary1 extends NodeWrapper {

        public TPrimary1(ParseTreeNode node) {
            super(node);
        }

        public TPrimary tPrimary() {
            return new TPrimary(get(0));
        }

        public String name() {
            return get(2, TokenType.NAME);
        }
    }

    /**
     * t_primary parameters &t_lookahead
     */
    public static final class TPrimary2 extends NodeWrapper {

        public TPrimary2(ParseTreeNode node) {
            super(node);
        }

        public TPrimary tPrimary() {
            return new TPrimary(get(0));
        }

        public Parameters parameters() {
            return new Parameters(get(1));
        }
    }

    /**
     * t_primary subscript &t_lookahead
     */
    public static final class TPrimary3 extends NodeWrapper {

        public TPrimary3(ParseTreeNode node) {
            super(node);
        }

        public TPrimary tPrimary() {
            return new TPrimary(get(0));
        }

        public Subscript subscript() {
            return new Subscript(get(1));
        }
    }

    /**
     * atom &t_lookahead
     */
    public static final class TPrimary4 extends NodeWrapper {

        public TPrimary4(ParseTreeNode node) {
            super(node);
        }

        public Atom atom() {
            return new Atom(get(0));
        }
    }
}

package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * target:
 * *   | t_primary '.' NAME !t_lookahead
 * *   | t_primary subscript !t_lookahead
 * *   | NAME
 * *   | '(' targetlist ')'
 */
public final class Target extends NodeWrapper {

    public Target(ParseTreeNode node) {
        super(node);
    }

    public Target1 target1() {
        return new Target1(get(0));
    }

    public boolean hasTarget1() {
        return has(0);
    }

    public Target2 target2() {
        return new Target2(get(1));
    }

    public boolean hasTarget2() {
        return has(1);
    }

    public String name() {
        return get(2, TokenType.NAME);
    }

    public boolean hasName() {
        return has(2);
    }

    public Target4 lparTargetlistRpar() {
        return new Target4(get(3));
    }

    public boolean hasLparTargetlistRpar() {
        return has(3);
    }

    /**
     * t_primary '.' NAME !t_lookahead
     */
    public static final class Target1 extends NodeWrapper {

        public Target1(ParseTreeNode node) {
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
     * t_primary subscript !t_lookahead
     */
    public static final class Target2 extends NodeWrapper {

        public Target2(ParseTreeNode node) {
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
     * '(' targetlist ')'
     */
    public static final class Target4 extends NodeWrapper {

        public Target4(ParseTreeNode node) {
            super(node);
        }

        public Targetlist targetlist() {
            return new Targetlist(get(1));
        }
    }
}

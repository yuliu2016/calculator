package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * target: NAME | '(' targetlist ')' | '*' target | primary
 */
public final class Target extends NodeWrapper {

    public Target(ParseTreeNode node) {
        super(node);
    }

    public String name() {
        return get(0, TokenType.NAME);
    }

    public boolean hasName() {
        return has(0);
    }

    public Target2 lparTargetlistRpar() {
        return new Target2(get(1));
    }

    public boolean hasLparTargetlistRpar() {
        return has(1);
    }

    public Target3 timesTarget() {
        return new Target3(get(2));
    }

    public boolean hasTimesTarget() {
        return has(2);
    }

    public Primary primary() {
        return new Primary(get(3));
    }

    public boolean hasPrimary() {
        return has(3);
    }

    /**
     * '(' targetlist ')'
     */
    public static final class Target2 extends NodeWrapper {

        public Target2(ParseTreeNode node) {
            super(node);
        }

        public Targetlist targetlist() {
            return new Targetlist(get(1));
        }
    }

    /**
     * '*' target
     */
    public static final class Target3 extends NodeWrapper {

        public Target3(ParseTreeNode node) {
            super(node);
        }

        public Target target() {
            return new Target(get(1));
        }
    }
}

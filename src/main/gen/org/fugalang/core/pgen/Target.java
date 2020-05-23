package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.FugaRules;
import org.fugalang.core.token.TokenType;

/**
 * target: 'NAME' | '(' 'targetlist' ')' | '*' 'target' | 'primary'
 */
public final class Target extends NodeWrapper {

    public Target(ParseTreeNode node) {
        super(FugaRules.TARGET, node);
    }

    public String name() {
        return get(0, TokenType.NAME);
    }

    public boolean hasName() {
        return has(0);
    }

    public Target2 targetlist() {
        return get(1, Target2.class);
    }

    public boolean hasTargetlist() {
        return has(1);
    }

    public Target3 target() {
        return get(2, Target3.class);
    }

    public boolean hasTarget() {
        return has(2);
    }

    public Primary primary() {
        return get(3, Primary.class);
    }

    public boolean hasPrimary() {
        return has(3);
    }

    /**
     * '(' 'targetlist' ')'
     */
    public static final class Target2 extends NodeWrapper {

        public Target2(ParseTreeNode node) {
            super(FugaRules.TARGET_2, node);
        }

        public Targetlist targetlist() {
            return get(1, Targetlist.class);
        }
    }

    /**
     * '*' 'target'
     */
    public static final class Target3 extends NodeWrapper {

        public Target3(ParseTreeNode node) {
            super(FugaRules.TARGET_3, node);
        }

        public Target target() {
            return get(1, Target.class);
        }
    }
}

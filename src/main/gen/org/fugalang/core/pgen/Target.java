package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;
import org.fugalang.core.token.TokenType;

/**
 * target: 'NAME' | '(' 'targetlist' ')' | '*' 'target' | 'primary'
 */
public final class Target extends NodeWrapper {

    public Target(ParseTreeNode node) {
        super(ParserRules.TARGET, node);
    }

    public String name() {
        return get(0, TokenType.NAME);
    }

    public boolean hasName() {
        return has(0);
    }

    public Target2 targetlist() {
        return get(1, Target2::new);
    }

    public boolean hasTargetlist() {
        return has(1);
    }

    public Target3 target() {
        return get(2, Target3::new);
    }

    public boolean hasTarget() {
        return has(2);
    }

    public Primary primary() {
        return get(3, Primary::new);
    }

    public boolean hasPrimary() {
        return has(3);
    }

    /**
     * '(' 'targetlist' ')'
     */
    public static final class Target2 extends NodeWrapper {

        public Target2(ParseTreeNode node) {
            super(ParserRules.TARGET_2, node);
        }

        public Targetlist targetlist() {
            return get(1, Targetlist::new);
        }
    }

    /**
     * '*' 'target'
     */
    public static final class Target3 extends NodeWrapper {

        public Target3(ParseTreeNode node) {
            super(ParserRules.TARGET_3, node);
        }

        public Target target() {
            return get(1, Target::new);
        }
    }
}

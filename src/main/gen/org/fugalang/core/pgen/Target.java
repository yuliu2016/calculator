package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * target: 'NAME' | '(' 'targetlist' ')' | '*' 'target' | 'primary'
 */
public final class Target extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("target", RuleType.Disjunction);

    public static Target of(ParseTreeNode node) {
        return new Target(node);
    }

    private Target(ParseTreeNode node) {
        super(RULE, node);
    }

    public String name() {
        return get(0, TokenType.NAME);
    }

    public boolean hasName() {
        return has(0, TokenType.NAME);
    }

    public Target2 targetlist() {
        return get(1, Target2::of);
    }

    public boolean hasTargetlist() {
        return has(1, Target2.RULE);
    }

    public Target3 target() {
        return get(2, Target3::of);
    }

    public boolean hasTarget() {
        return has(2, Target3.RULE);
    }

    public Primary primary() {
        return get(3, Primary::of);
    }

    public boolean hasPrimary() {
        return has(3, Primary.RULE);
    }

    /**
     * '(' 'targetlist' ')'
     */
    public static final class Target2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("target:2", RuleType.Conjunction);

        public static Target2 of(ParseTreeNode node) {
            return new Target2(node);
        }

        private Target2(ParseTreeNode node) {
            super(RULE, node);
        }

        public Targetlist targetlist() {
            return get(1, Targetlist::of);
        }
    }

    /**
     * '*' 'target'
     */
    public static final class Target3 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("target:3", RuleType.Conjunction);

        public static Target3 of(ParseTreeNode node) {
            return new Target3(node);
        }

        private Target3(ParseTreeNode node) {
            super(RULE, node);
        }

        public Target target() {
            return get(1, Target::of);
        }
    }
}

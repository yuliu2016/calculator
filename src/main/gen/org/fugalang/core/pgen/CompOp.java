package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * comp_op: '<' | '>' | '==' | '>=' | '<=' | '!=' | 'in' | 'not' 'in' | 'is' | 'is' 'not'
 */
public final class CompOp extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("comp_op", RuleType.Disjunction);

    public static CompOp of(ParseTreeNode node) {
        return new CompOp(node);
    }

    private CompOp(ParseTreeNode node) {
        super(RULE, node);
    }

    public boolean isLess() {
        return is(0);
    }

    public boolean isGreater() {
        return is(1);
    }

    public boolean isEqual() {
        return is(2);
    }

    public boolean isMoreEqual() {
        return is(3);
    }

    public boolean isLessEqual() {
        return is(4);
    }

    public boolean isNequal() {
        return is(5);
    }

    public boolean isIn() {
        return is(6);
    }

    public CompOp8 notIn() {
        return get(7, CompOp8::of);
    }

    public boolean hasNotIn() {
        return has(7, CompOp8.RULE);
    }

    public boolean isIs() {
        return is(8);
    }

    public CompOp10 isNot() {
        return get(9, CompOp10::of);
    }

    public boolean hasIsNot() {
        return has(9, CompOp10.RULE);
    }

    /**
     * 'not' 'in'
     */
    public static final class CompOp8 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("comp_op:8", RuleType.Conjunction);

        public static CompOp8 of(ParseTreeNode node) {
            return new CompOp8(node);
        }

        private CompOp8(ParseTreeNode node) {
            super(RULE, node);
        }
    }

    /**
     * 'is' 'not'
     */
    public static final class CompOp10 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("comp_op:10", RuleType.Conjunction);

        public static CompOp10 of(ParseTreeNode node) {
            return new CompOp10(node);
        }

        private CompOp10(ParseTreeNode node) {
            super(RULE, node);
        }
    }
}

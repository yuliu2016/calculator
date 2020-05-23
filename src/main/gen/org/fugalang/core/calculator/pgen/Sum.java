package org.fugalang.core.calculator.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * sum: 'term' (('+' | '-') 'term')*
 */
public final class Sum extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("sum", RuleType.Conjunction);

    public static Sum of(ParseTreeNode node) {
        return new Sum(node);
    }

    private Sum(ParseTreeNode node) {
        super(RULE, node);
    }

    public Term term() {
        return get(0, Term::of);
    }

    public List<Sum2> sum2s() {
        return getList(1, Sum2::of);
    }

    /**
     * ('+' | '-') 'term'
     */
    public static final class Sum2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("sum:2", RuleType.Conjunction);

        public static Sum2 of(ParseTreeNode node) {
            return new Sum2(node);
        }

        private Sum2(ParseTreeNode node) {
            super(RULE, node);
        }

        public Sum21 plusOrMinus() {
            return get(0, Sum21::of);
        }

        public Term term() {
            return get(1, Term::of);
        }
    }

    /**
     * '+' | '-'
     */
    public static final class Sum21 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("sum:2:1", RuleType.Disjunction);

        public static Sum21 of(ParseTreeNode node) {
            return new Sum21(node);
        }

        private Sum21(ParseTreeNode node) {
            super(RULE, node);
        }

        public boolean isPlus() {
            return is(0);
        }

        public boolean isMinus() {
            return is(1);
        }
    }
}

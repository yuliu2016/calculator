package org.fugalang.core.calculator.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * term: 'factor' (('*' | '/' | '%') 'factor')*
 */
public final class Term extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("term", RuleType.Conjunction);

    public static Term of(ParseTreeNode node) {
        return new Term(node);
    }

    private Term(ParseTreeNode node) {
        super(RULE, node);
    }

    public Factor factor() {
        return get(0, Factor::of);
    }

    public List<Term2> term2s() {
        return getList(1, Term2::of);
    }

    /**
     * ('*' | '/' | '%') 'factor'
     */
    public static final class Term2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("term:2", RuleType.Conjunction);

        public static Term2 of(ParseTreeNode node) {
            return new Term2(node);
        }

        private Term2(ParseTreeNode node) {
            super(RULE, node);
        }

        public Term21 term21() {
            return get(0, Term21::of);
        }

        public Factor factor() {
            return get(1, Factor::of);
        }
    }

    /**
     * '*' | '/' | '%'
     */
    public static final class Term21 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("term:2:1", RuleType.Disjunction);

        public static Term21 of(ParseTreeNode node) {
            return new Term21(node);
        }

        private Term21(ParseTreeNode node) {
            super(RULE, node);
        }

        public boolean isTimes() {
            return is(0);
        }

        public boolean isDiv() {
            return is(1);
        }

        public boolean isModulus() {
            return is(2);
        }
    }
}

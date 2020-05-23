package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * except_clause: 'except' ['expr' ['as' 'NAME']]
 */
public final class ExceptClause extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("except_clause", RuleType.Conjunction);

    public static ExceptClause of(ParseTreeNode node) {
        return new ExceptClause(node);
    }

    private ExceptClause(ParseTreeNode node) {
        super(RULE, node);
    }

    public ExceptClause2 exceptClause2() {
        return get(1, ExceptClause2::of);
    }

    public boolean hasExceptClause2() {
        return has(1, ExceptClause2.RULE);
    }

    /**
     * 'expr' ['as' 'NAME']
     */
    public static final class ExceptClause2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("except_clause:2", RuleType.Conjunction);

        public static ExceptClause2 of(ParseTreeNode node) {
            return new ExceptClause2(node);
        }

        private ExceptClause2(ParseTreeNode node) {
            super(RULE, node);
        }

        public Expr expr() {
            return get(0, Expr::of);
        }

        public ExceptClause22 asName() {
            return get(1, ExceptClause22::of);
        }

        public boolean hasAsName() {
            return has(1, ExceptClause22.RULE);
        }
    }

    /**
     * 'as' 'NAME'
     */
    public static final class ExceptClause22 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("except_clause:2:2", RuleType.Conjunction);

        public static ExceptClause22 of(ParseTreeNode node) {
            return new ExceptClause22(node);
        }

        private ExceptClause22(ParseTreeNode node) {
            super(RULE, node);
        }

        public String name() {
            return get(1, TokenType.NAME);
        }
    }
}

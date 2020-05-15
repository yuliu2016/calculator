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
        return ExceptClause2.of(getItem(1));
    }

    public boolean hasExceptClause2() {
        return hasItemOfRule(1, ExceptClause2.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consumeToken("except");
        if (r) ExceptClause2.parse(t, lv + 1);
        t.exit(r);
        return r;
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
            return Expr.of(getItem(0));
        }

        public ExceptClause22 exceptClause22() {
            return ExceptClause22.of(getItem(1));
        }

        public boolean hasExceptClause22() {
            return hasItemOfRule(1, ExceptClause22.RULE);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = Expr.parse(t, lv + 1);
            if (r) ExceptClause22.parse(t, lv + 1);
            t.exit(r);
            return r;
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
            return getItemOfType(1, TokenType.NAME);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consumeToken("as");
            r = r && t.consumeToken(TokenType.NAME);
            t.exit(r);
            return r;
        }
    }
}

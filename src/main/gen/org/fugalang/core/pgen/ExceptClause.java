package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;
import org.fugalang.core.token.TokenType;

/**
 * except_clause: 'except' ['expr' ['as' 'NAME']]
 */
public final class ExceptClause extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("except_clause", RuleType.Conjunction, true);

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

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("except");
        if (result) ExceptClause2.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * 'expr' ['as' 'NAME']
     */
    public static final class ExceptClause2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("except_clause:2", RuleType.Conjunction, false);

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

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = Expr.parse(parseTree, level + 1);
            if (result) ExceptClause22.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * 'as' 'NAME'
     */
    public static final class ExceptClause22 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("except_clause:2:2", RuleType.Conjunction, false);

        public static ExceptClause22 of(ParseTreeNode node) {
            return new ExceptClause22(node);
        }

        private ExceptClause22(ParseTreeNode node) {
            super(RULE, node);
        }

        public String name() {
            return getItemOfType(1,TokenType.NAME);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("as");
            result = result && parseTree.consumeToken(TokenType.NAME);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}

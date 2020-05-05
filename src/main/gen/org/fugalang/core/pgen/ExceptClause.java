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

    @Override
    protected void buildRule() {
        addRequired(isTokenExcept(), "except");
        addOptional(exceptClause2());
    }

    public boolean isTokenExcept() {
        var element = getItem(0);
        element.failIfAbsent();
        return element.asBoolean();
    }

    public ExceptClause2 exceptClause2() {
        var element = getItem(1);
        if (!element.isPresent(ExceptClause2.RULE)) {
            return null;
        }
        return ExceptClause2.of(element);
    }

    public boolean hasExceptClause2() {
        var element = getItem(1);
        return element.isPresent(ExceptClause2.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
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

        @Override
        protected void buildRule() {
            addRequired(expr());
            addOptional(exceptClause22());
        }

        public Expr expr() {
            var element = getItem(0);
            element.failIfAbsent(Expr.RULE);
            return Expr.of(element);
        }

        public ExceptClause22 exceptClause22() {
            var element = getItem(1);
            if (!element.isPresent(ExceptClause22.RULE)) {
                return null;
            }
            return ExceptClause22.of(element);
        }

        public boolean hasExceptClause22() {
            var element = getItem(1);
            return element.isPresent(ExceptClause22.RULE);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
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

        @Override
        protected void buildRule() {
            addRequired(isTokenAs(), "as");
            addRequired(name());
        }

        public boolean isTokenAs() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public String name() {
            var element = getItem(1);
            element.failIfAbsent(TokenType.NAME);
            return element.asString();
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken("as");
            result = result && parseTree.consumeToken(TokenType.NAME);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}

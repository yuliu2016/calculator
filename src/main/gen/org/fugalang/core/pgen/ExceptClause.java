package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

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
        addRequired("isTokenExcept", isTokenExcept());
        addOptional("exceptClause2", exceptClause2());
    }

    public boolean isTokenExcept() {
        var element = getItem(0);
        return element.asBoolean();
    }

    public ExceptClause2 exceptClause2() {
        var element = getItem(1);
        if (!element.isPresent()) return null;
        return ExceptClause2.of(element);
    }

    public boolean hasExceptClause2() {
        return exceptClause2() != null;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeTokenLiteral("except");
        ExceptClause2.parse(parseTree, level + 1);

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
            addRequired("expr", expr());
            addOptional("exceptClause22", exceptClause22());
        }

        public Expr expr() {
            var element = getItem(0);
            if (!element.isPresent()) return null;
            return Expr.of(element);
        }

        public ExceptClause22 exceptClause22() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return ExceptClause22.of(element);
        }

        public boolean hasExceptClause22() {
            return exceptClause22() != null;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = Expr.parse(parseTree, level + 1);
            ExceptClause22.parse(parseTree, level + 1);

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
            addRequired("isTokenAs", isTokenAs());
            addRequired("name", name());
        }

        public boolean isTokenAs() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public String name() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return (String) element.asObject();
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral("as");
            result = result && parseTree.consumeTokenType("NAME");

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}

package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * except_clause: 'except' ['expr' ['as' 'NAME']]
 */
public final class ExceptClause extends ConjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("except_clause", RuleType.Conjunction, true);

    private final boolean isTokenExcept;
    private final ExceptClause2 exceptClause2;

    public ExceptClause(
            boolean isTokenExcept,
            ExceptClause2 exceptClause2
    ) {
        this.isTokenExcept = isTokenExcept;
        this.exceptClause2 = exceptClause2;
    }

    @Override
    protected void buildRule() {
        addRequired("isTokenExcept", isTokenExcept());
        addOptional("exceptClause2", exceptClause2());
    }

    public boolean isTokenExcept() {
        return isTokenExcept;
    }

    public ExceptClause2 exceptClause2() {
        return exceptClause2;
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
    public static final class ExceptClause2 extends ConjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("except_clause:2", RuleType.Conjunction, false);

        private final Expr expr;
        private final ExceptClause22 exceptClause22;

        public ExceptClause2(
                Expr expr,
                ExceptClause22 exceptClause22
        ) {
            this.expr = expr;
            this.exceptClause22 = exceptClause22;
        }

        @Override
        protected void buildRule() {
            addRequired("expr", expr());
            addOptional("exceptClause22", exceptClause22());
        }

        public Expr expr() {
            return expr;
        }

        public ExceptClause22 exceptClause22() {
            return exceptClause22;
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
    public static final class ExceptClause22 extends ConjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("except_clause:2:2", RuleType.Conjunction, false);

        private final boolean isTokenAs;
        private final String name;

        public ExceptClause22(
                boolean isTokenAs,
                String name
        ) {
            this.isTokenAs = isTokenAs;
            this.name = name;
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenAs", isTokenAs());
            addRequired("name", name());
        }

        public boolean isTokenAs() {
            return isTokenAs;
        }

        public String name() {
            return name;
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

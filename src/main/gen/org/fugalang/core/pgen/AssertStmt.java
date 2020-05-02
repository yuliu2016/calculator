package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * assert_stmt: 'assert' 'expr' [',' 'expr']
 */
public final class AssertStmt extends ConjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("assert_stmt", RuleType.Conjunction, true);

    private final boolean isTokenAssert;
    private final Expr expr;
    private final AssertStmt3 assertStmt3;

    public AssertStmt(
            boolean isTokenAssert,
            Expr expr,
            AssertStmt3 assertStmt3
    ) {
        this.isTokenAssert = isTokenAssert;
        this.expr = expr;
        this.assertStmt3 = assertStmt3;
    }

    @Override
    protected void buildRule() {
        addRequired("isTokenAssert", isTokenAssert());
        addRequired("expr", expr());
        addOptional("assertStmt3", assertStmt3());
    }

    public boolean isTokenAssert() {
        return isTokenAssert;
    }

    public Expr expr() {
        return expr;
    }

    public AssertStmt3 assertStmt3() {
        return assertStmt3;
    }

    public boolean hasAssertStmt3() {
        return assertStmt3() != null;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeTokenLiteral("assert");
        result = result && Expr.parse(parseTree, level + 1);
        AssertStmt3.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * ',' 'expr'
     */
    public static final class AssertStmt3 extends ConjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("assert_stmt:3", RuleType.Conjunction, false);

        private final boolean isTokenComma;
        private final Expr expr;

        public AssertStmt3(
                boolean isTokenComma,
                Expr expr
        ) {
            this.isTokenComma = isTokenComma;
            this.expr = expr;
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenComma", isTokenComma());
            addRequired("expr", expr());
        }

        public boolean isTokenComma() {
            return isTokenComma;
        }

        public Expr expr() {
            return expr;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral(",");
            result = result && Expr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}

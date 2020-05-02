package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * assert_stmt: 'assert' 'expr' [',' 'expr']
 */
public final class AssertStmt extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("assert_stmt", RuleType.Conjunction, true);

    public static AssertStmt of(ParseTreeNode node) {
        return new AssertStmt(node);
    }

    private AssertStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addRequired("isTokenAssert", isTokenAssert());
        addRequired("expr", expr());
        addOptional("assertStmt3", assertStmt3());
    }

    public boolean isTokenAssert() {
        var element = getItem(0);
        return element.asBoolean();
    }

    public Expr expr() {
        var element = getItem(1);
        if (!element.isPresent()) return null;
        return Expr.of(element);
    }

    public AssertStmt3 assertStmt3() {
        var element = getItem(2);
        if (!element.isPresent()) return null;
        return AssertStmt3.of(element);
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
    public static final class AssertStmt3 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("assert_stmt:3", RuleType.Conjunction, false);

        public static AssertStmt3 of(ParseTreeNode node) {
            return new AssertStmt3(node);
        }

        private AssertStmt3(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenComma", isTokenComma());
            addRequired("expr", expr());
        }

        public boolean isTokenComma() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public Expr expr() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return Expr.of(element);
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
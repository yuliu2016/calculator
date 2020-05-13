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

    public boolean isTokenAssert() {
        return true;
    }

    public Expr expr() {
        return Expr.of(getItem(1));
    }

    public AssertStmt3 assertStmt3() {
        return AssertStmt3.of(getItem(2));
    }

    public boolean hasAssertStmt3() {
        return hasItemOfRule(2, AssertStmt3.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = parseTree.consumeToken("assert");
        result = result && Expr.parse(parseTree, level + 1);
        if (result) AssertStmt3.parse(parseTree, level + 1);

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

        public boolean isTokenComma() {
            return true;
        }

        public Expr expr() {
            return Expr.of(getItem(1));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(",");
            result = result && Expr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}

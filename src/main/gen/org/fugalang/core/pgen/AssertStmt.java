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

    public Expr expr() {
        return Expr.of(getItem(1));
    }

    public AssertStmt3 assertStmt3() {
        return AssertStmt3.of(getItem(2));
    }

    public boolean hasAssertStmt3() {
        return hasItemOfRule(2, AssertStmt3.RULE);
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = t.consumeToken("assert");
        r = r && Expr.parse(t, l + 1);
        if (r) AssertStmt3.parse(t, l + 1);
        t.exit(r);
        return r;
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

        public Expr expr() {
            return Expr.of(getItem(1));
        }

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = t.consumeToken(",");
            r = r && Expr.parse(t, l + 1);
            t.exit(r);
            return r;
        }
    }
}

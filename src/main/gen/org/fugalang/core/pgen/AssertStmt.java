package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * assert_stmt: 'assert' 'expr' [',' 'expr']
 */
public final class AssertStmt extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("assert_stmt", RuleType.Conjunction);

    public static AssertStmt of(ParseTreeNode node) {
        return new AssertStmt(node);
    }

    private AssertStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    public Expr expr() {
        return Expr.of(get(1));
    }

    public AssertStmt3 expr1() {
        return AssertStmt3.of(get(2));
    }

    public boolean hasExpr1() {
        return has(2, AssertStmt3.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consume("assert");
        r = r && Expr.parse(t, lv + 1);
        if (r) AssertStmt3.parse(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * ',' 'expr'
     */
    public static final class AssertStmt3 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("assert_stmt:3", RuleType.Conjunction);

        public static AssertStmt3 of(ParseTreeNode node) {
            return new AssertStmt3(node);
        }

        private AssertStmt3(ParseTreeNode node) {
            super(RULE, node);
        }

        public Expr expr() {
            return Expr.of(get(1));
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume(",");
            r = r && Expr.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}

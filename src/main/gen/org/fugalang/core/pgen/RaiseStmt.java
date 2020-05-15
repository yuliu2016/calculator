package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * raise_stmt: 'raise' ['expr' ['from' 'expr']]
 */
public final class RaiseStmt extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("raise_stmt", RuleType.Conjunction);

    public static RaiseStmt of(ParseTreeNode node) {
        return new RaiseStmt(node);
    }

    private RaiseStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    public RaiseStmt2 raiseStmt2() {
        return RaiseStmt2.of(getItem(1));
    }

    public boolean hasRaiseStmt2() {
        return hasItemOfRule(1, RaiseStmt2.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consumeToken("raise");
        if (r) RaiseStmt2.parse(t, lv + 1);
        t.exit(r);
        return r;
    }

    /**
     * 'expr' ['from' 'expr']
     */
    public static final class RaiseStmt2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("raise_stmt:2", RuleType.Conjunction);

        public static RaiseStmt2 of(ParseTreeNode node) {
            return new RaiseStmt2(node);
        }

        private RaiseStmt2(ParseTreeNode node) {
            super(RULE, node);
        }

        public Expr expr() {
            return Expr.of(getItem(0));
        }

        public RaiseStmt22 raiseStmt22() {
            return RaiseStmt22.of(getItem(1));
        }

        public boolean hasRaiseStmt22() {
            return hasItemOfRule(1, RaiseStmt22.RULE);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = Expr.parse(t, lv + 1);
            if (r) RaiseStmt22.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }

    /**
     * 'from' 'expr'
     */
    public static final class RaiseStmt22 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("raise_stmt:2:2", RuleType.Conjunction);

        public static RaiseStmt22 of(ParseTreeNode node) {
            return new RaiseStmt22(node);
        }

        private RaiseStmt22(ParseTreeNode node) {
            super(RULE, node);
        }

        public Expr expr() {
            return Expr.of(getItem(1));
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consumeToken("from");
            r = r && Expr.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}

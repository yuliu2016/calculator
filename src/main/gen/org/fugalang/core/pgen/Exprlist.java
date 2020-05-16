package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * exprlist: 'expr' (',' 'expr')* [',']
 */
public final class Exprlist extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("exprlist", RuleType.Conjunction);

    public static Exprlist of(ParseTreeNode node) {
        return new Exprlist(node);
    }

    private Exprlist(ParseTreeNode node) {
        super(RULE, node);
    }

    public Expr expr() {
        return get(0, Expr::of);
    }

    public List<Exprlist2> exprs() {
        return getList(1, Exprlist2::of);
    }

    public boolean isComma() {
        return is(2);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = Expr.parse(t, lv + 1);
        if (r) parseExprs(t, lv);
        if (r) t.consume(",");
        t.exit(r);
        return r;
    }

    private static void parseExprs(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!Exprlist2.parse(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ',' 'expr'
     */
    public static final class Exprlist2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("exprlist:2", RuleType.Conjunction);

        public static Exprlist2 of(ParseTreeNode node) {
            return new Exprlist2(node);
        }

        private Exprlist2(ParseTreeNode node) {
            super(RULE, node);
        }

        public Expr expr() {
            return get(1, Expr::of);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (t.recursionGuard(lv)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume(",");
            r = r && Expr.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}

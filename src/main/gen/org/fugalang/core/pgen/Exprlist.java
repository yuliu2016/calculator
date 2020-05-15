package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * exprlist: 'expr' (',' 'expr')* [',']
 */
public final class Exprlist extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("exprlist", RuleType.Conjunction, true);

    public static Exprlist of(ParseTreeNode node) {
        return new Exprlist(node);
    }

    private Exprlist(ParseTreeNode node) {
        super(RULE, node);
    }

    public Expr expr() {
        return Expr.of(getItem(0));
    }

    public List<Exprlist2> exprlist2List() {
        return getList(1, Exprlist2::of);
    }

    public boolean isTokenComma() {
        return getBoolean(2);
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = Expr.parse(t, l + 1);
        if (r) parseExprlist2List(t, l);
        if (r) t.consumeToken(",");
        t.exit(r);
        return r;
    }

    private static void parseExprlist2List(ParseTree t, int l) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!Exprlist2.parse(t, l + 1)) break;
            if (t.guardLoopExit(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ',' 'expr'
     */
    public static final class Exprlist2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("exprlist:2", RuleType.Conjunction, false);

        public static Exprlist2 of(ParseTreeNode node) {
            return new Exprlist2(node);
        }

        private Exprlist2(ParseTreeNode node) {
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

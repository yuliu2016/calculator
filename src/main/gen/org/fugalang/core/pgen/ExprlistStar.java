package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * exprlist_star: 'expr_or_star' (',' 'expr_or_star')* [',']
 */
public final class ExprlistStar extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("exprlist_star", RuleType.Conjunction, true);

    public static ExprlistStar of(ParseTreeNode node) {
        return new ExprlistStar(node);
    }

    private ExprlistStar(ParseTreeNode node) {
        super(RULE, node);
    }

    public ExprOrStar exprOrStar() {
        return ExprOrStar.of(getItem(0));
    }

    public List<ExprlistStar2> exprlistStar2List() {
        return getList(1, ExprlistStar2::of);
    }

    public boolean isTokenComma() {
        return getBoolean(2);
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = ExprOrStar.parse(t, l + 1);
        if (r) parseExprlistStar2List(t, l);
        if (r) t.consumeToken(",");
        t.exit(r);
        return r;
    }

    private static void parseExprlistStar2List(ParseTree t, int l) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!ExprlistStar2.parse(t, l + 1)) break;
            if (t.guardLoopExit(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ',' 'expr_or_star'
     */
    public static final class ExprlistStar2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("exprlist_star:2", RuleType.Conjunction, false);

        public static ExprlistStar2 of(ParseTreeNode node) {
            return new ExprlistStar2(node);
        }

        private ExprlistStar2(ParseTreeNode node) {
            super(RULE, node);
        }

        public ExprOrStar exprOrStar() {
            return ExprOrStar.of(getItem(1));
        }

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = t.consumeToken(",");
            r = r && ExprOrStar.parse(t, l + 1);
            t.exit(r);
            return r;
        }
    }
}

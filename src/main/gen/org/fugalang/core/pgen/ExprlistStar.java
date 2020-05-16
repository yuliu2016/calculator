package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * exprlist_star: 'expr_or_star' (',' 'expr_or_star')* [',']
 */
public final class ExprlistStar extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("exprlist_star", RuleType.Conjunction);

    public static ExprlistStar of(ParseTreeNode node) {
        return new ExprlistStar(node);
    }

    private ExprlistStar(ParseTreeNode node) {
        super(RULE, node);
    }

    public ExprOrStar exprOrStar() {
        return ExprOrStar.of(get(0));
    }

    public List<ExprlistStar2> exprOrStarList() {
        return getList(1, ExprlistStar2::of);
    }

    public boolean isComma() {
        return is(2);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = ExprOrStar.parse(t, lv + 1);
        if (r) parseExprOrStarList(t, lv);
        if (r) t.consume(",");
        t.exit(r);
        return r;
    }

    private static void parseExprOrStarList(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!ExprlistStar2.parse(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ',' 'expr_or_star'
     */
    public static final class ExprlistStar2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("exprlist_star:2", RuleType.Conjunction);

        public static ExprlistStar2 of(ParseTreeNode node) {
            return new ExprlistStar2(node);
        }

        private ExprlistStar2(ParseTreeNode node) {
            super(RULE, node);
        }

        public ExprOrStar exprOrStar() {
            return ExprOrStar.of(get(1));
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume(",");
            r = r && ExprOrStar.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}

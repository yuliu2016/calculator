package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * set_maker: 'expr_or_star' (',' 'expr_or_star')* [',']
 */
public final class SetMaker extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("set_maker", RuleType.Conjunction);

    public static SetMaker of(ParseTreeNode node) {
        return new SetMaker(node);
    }

    private SetMaker(ParseTreeNode node) {
        super(RULE, node);
    }

    public ExprOrStar exprOrStar() {
        return ExprOrStar.of(getItem(0));
    }

    public List<SetMaker2> setMaker2List() {
        return getList(1, SetMaker2::of);
    }

    public boolean isTokenComma() {
        return getBoolean(2);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = ExprOrStar.parse(t, lv + 1);
        if (r) parseSetMaker2List(t, lv);
        if (r) t.consumeToken(",");
        t.exit(r);
        return r;
    }

    private static void parseSetMaker2List(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!SetMaker2.parse(t, lv + 1)) break;
            if (t.guardLoopExit(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ',' 'expr_or_star'
     */
    public static final class SetMaker2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("set_maker:2", RuleType.Conjunction);

        public static SetMaker2 of(ParseTreeNode node) {
            return new SetMaker2(node);
        }

        private SetMaker2(ParseTreeNode node) {
            super(RULE, node);
        }

        public ExprOrStar exprOrStar() {
            return ExprOrStar.of(getItem(1));
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consumeToken(",");
            r = r && ExprOrStar.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}

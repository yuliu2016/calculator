package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * named_expr_list: 'named_expr_star' (',' 'named_expr_star')* [',']
 */
public final class NamedExprList extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("named_expr_list", RuleType.Conjunction);

    public static NamedExprList of(ParseTreeNode node) {
        return new NamedExprList(node);
    }

    private NamedExprList(ParseTreeNode node) {
        super(RULE, node);
    }

    public NamedExprStar namedExprStar() {
        return get(0, NamedExprStar::of);
    }

    public List<NamedExprList2> namedExprStars() {
        return getList(1, NamedExprList2::of);
    }

    public boolean isComma() {
        return is(2);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = NamedExprStar.parse(t, lv + 1);
        if (r) parseNamedExprStars(t, lv);
        if (r) t.consume(",");
        t.exit(r);
        return r;
    }

    private static void parseNamedExprStars(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!NamedExprList2.parse(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ',' 'named_expr_star'
     */
    public static final class NamedExprList2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("named_expr_list:2", RuleType.Conjunction);

        public static NamedExprList2 of(ParseTreeNode node) {
            return new NamedExprList2(node);
        }

        private NamedExprList2(ParseTreeNode node) {
            super(RULE, node);
        }

        public NamedExprStar namedExprStar() {
            return get(1, NamedExprStar::of);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume(",");
            r = r && NamedExprStar.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}

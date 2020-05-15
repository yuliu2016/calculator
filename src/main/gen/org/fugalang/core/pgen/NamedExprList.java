package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * named_expr_list: 'named_expr_star' (',' 'named_expr_star')* [',']
 */
public final class NamedExprList extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("named_expr_list", RuleType.Conjunction, true);

    public static NamedExprList of(ParseTreeNode node) {
        return new NamedExprList(node);
    }

    private NamedExprList(ParseTreeNode node) {
        super(RULE, node);
    }

    public NamedExprStar namedExprStar() {
        return NamedExprStar.of(getItem(0));
    }

    public List<NamedExprList2> namedExprList2List() {
        return getList(1, NamedExprList2::of);
    }

    public boolean isTokenComma() {
        return getBoolean(2);
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = NamedExprStar.parse(t, l + 1);
        if (r) parseNamedExprList2List(t, l);
        if (r) t.consumeToken(",");
        t.exit(r);
        return r;
    }

    private static void parseNamedExprList2List(ParseTree t, int l) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!NamedExprList2.parse(t, l + 1)) break;
            if (t.guardLoopExit(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ',' 'named_expr_star'
     */
    public static final class NamedExprList2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("named_expr_list:2", RuleType.Conjunction, false);

        public static NamedExprList2 of(ParseTreeNode node) {
            return new NamedExprList2(node);
        }

        private NamedExprList2(ParseTreeNode node) {
            super(RULE, node);
        }

        public NamedExprStar namedExprStar() {
            return NamedExprStar.of(getItem(1));
        }

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = t.consumeToken(",");
            r = r && NamedExprStar.parse(t, l + 1);
            t.exit(r);
            return r;
        }
    }
}

package org.fugalang.core.calculator.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * sum: 'term' (('+' | '-') 'term')*
 */
public final class Sum extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("sum", RuleType.Conjunction);

    public static Sum of(ParseTreeNode node) {
        return new Sum(node);
    }

    private Sum(ParseTreeNode node) {
        super(RULE, node);
    }

    public Term term() {
        return Term.of(getItem(0));
    }

    public List<Sum2> sum2List() {
        return getList(1, Sum2::of);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = Term.parse(t, lv + 1);
        if (r) parseSum2List(t, lv);
        t.exit(r);
        return r;
    }

    private static void parseSum2List(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!Sum2.parse(t, lv + 1)) break;
            if (t.guardLoopExit(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ('+' | '-') 'term'
     */
    public static final class Sum2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("sum:2", RuleType.Conjunction);

        public static Sum2 of(ParseTreeNode node) {
            return new Sum2(node);
        }

        private Sum2(ParseTreeNode node) {
            super(RULE, node);
        }

        public Sum21 sum21() {
            return Sum21.of(getItem(0));
        }

        public Term term() {
            return Term.of(getItem(1));
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = Sum21.parse(t, lv + 1);
            r = r && Term.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }

    /**
     * '+' | '-'
     */
    public static final class Sum21 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("sum:2:1", RuleType.Disjunction);

        public static Sum21 of(ParseTreeNode node) {
            return new Sum21(node);
        }

        private Sum21(ParseTreeNode node) {
            super(RULE, node);
        }

        public boolean isTokenPlus() {
            return getBoolean(0);
        }

        public boolean isTokenMinus() {
            return getBoolean(1);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consumeToken("+");
            r = r || t.consumeToken("-");
            t.exit(r);
            return r;
        }
    }
}

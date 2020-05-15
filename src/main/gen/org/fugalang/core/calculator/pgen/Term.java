package org.fugalang.core.calculator.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * term: 'factor' (('*' | '/' | '%') 'factor')*
 */
public final class Term extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("term", RuleType.Conjunction);

    public static Term of(ParseTreeNode node) {
        return new Term(node);
    }

    private Term(ParseTreeNode node) {
        super(RULE, node);
    }

    public Factor factor() {
        return Factor.of(getItem(0));
    }

    public List<Term2> term2List() {
        return getList(1, Term2::of);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = Factor.parse(t, lv + 1);
        if (r) parseTerm2List(t, lv);
        t.exit(r);
        return r;
    }

    private static void parseTerm2List(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!Term2.parse(t, lv + 1)) break;
            if (t.guardLoopExit(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ('*' | '/' | '%') 'factor'
     */
    public static final class Term2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("term:2", RuleType.Conjunction);

        public static Term2 of(ParseTreeNode node) {
            return new Term2(node);
        }

        private Term2(ParseTreeNode node) {
            super(RULE, node);
        }

        public Term21 term21() {
            return Term21.of(getItem(0));
        }

        public Factor factor() {
            return Factor.of(getItem(1));
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = Term21.parse(t, lv + 1);
            r = r && Factor.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }

    /**
     * '*' | '/' | '%'
     */
    public static final class Term21 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("term:2:1", RuleType.Disjunction);

        public static Term21 of(ParseTreeNode node) {
            return new Term21(node);
        }

        private Term21(ParseTreeNode node) {
            super(RULE, node);
        }

        public boolean isTokenTimes() {
            return getBoolean(0);
        }

        public boolean isTokenDiv() {
            return getBoolean(1);
        }

        public boolean isTokenModulus() {
            return getBoolean(2);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consumeToken("*");
            r = r || t.consumeToken("/");
            r = r || t.consumeToken("%");
            t.exit(r);
            return r;
        }
    }
}

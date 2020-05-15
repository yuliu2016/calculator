package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * term: 'pipeline' (('*' | '@' | '/' | '%' | '//') 'pipeline')*
 */
public final class Term extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("term", RuleType.Conjunction, true);

    public static Term of(ParseTreeNode node) {
        return new Term(node);
    }

    private Term(ParseTreeNode node) {
        super(RULE, node);
    }

    public Pipeline pipeline() {
        return Pipeline.of(getItem(0));
    }

    public List<Term2> term2List() {
        return getList(1, Term2::of);
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = Pipeline.parse(t, l + 1);
        if (r) parseTerm2List(t, l);
        t.exit(r);
        return r;
    }

    private static void parseTerm2List(ParseTree t, int l) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!Term2.parse(t, l + 1)) break;
            if (t.guardLoopExit(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ('*' | '@' | '/' | '%' | '//') 'pipeline'
     */
    public static final class Term2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("term:2", RuleType.Conjunction, false);

        public static Term2 of(ParseTreeNode node) {
            return new Term2(node);
        }

        private Term2(ParseTreeNode node) {
            super(RULE, node);
        }

        public Term21 term21() {
            return Term21.of(getItem(0));
        }

        public Pipeline pipeline() {
            return Pipeline.of(getItem(1));
        }

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = Term21.parse(t, l + 1);
            r = r && Pipeline.parse(t, l + 1);
            t.exit(r);
            return r;
        }
    }

    /**
     * '*' | '@' | '/' | '%' | '//'
     */
    public static final class Term21 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("term:2:1", RuleType.Disjunction, false);

        public static Term21 of(ParseTreeNode node) {
            return new Term21(node);
        }

        private Term21(ParseTreeNode node) {
            super(RULE, node);
        }

        public boolean isTokenTimes() {
            return getBoolean(0);
        }

        public boolean isTokenMatrixTimes() {
            return getBoolean(1);
        }

        public boolean isTokenDiv() {
            return getBoolean(2);
        }

        public boolean isTokenModulus() {
            return getBoolean(3);
        }

        public boolean isTokenFloorDiv() {
            return getBoolean(4);
        }

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = t.consumeToken("*");
            r = r || t.consumeToken("@");
            r = r || t.consumeToken("/");
            r = r || t.consumeToken("%");
            r = r || t.consumeToken("//");
            t.exit(r);
            return r;
        }
    }
}

package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * arglist: 'argument' (',' 'argument')* [',']
 */
public final class Arglist extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("arglist", RuleType.Conjunction, true);

    public static Arglist of(ParseTreeNode node) {
        return new Arglist(node);
    }

    private Arglist(ParseTreeNode node) {
        super(RULE, node);
    }

    public Argument argument() {
        return Argument.of(getItem(0));
    }

    public List<Arglist2> arglist2List() {
        return getList(1, Arglist2::of);
    }

    public boolean isTokenComma() {
        return getBoolean(2);
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = Argument.parse(t, l + 1);
        if (r) parseArglist2List(t, l);
        if (r) t.consumeToken(",");
        t.exit(r);
        return r;
    }

    private static void parseArglist2List(ParseTree t, int l) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!Arglist2.parse(t, l + 1)) break;
            if (t.guardLoopExit(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ',' 'argument'
     */
    public static final class Arglist2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("arglist:2", RuleType.Conjunction, false);

        public static Arglist2 of(ParseTreeNode node) {
            return new Arglist2(node);
        }

        private Arglist2(ParseTreeNode node) {
            super(RULE, node);
        }

        public Argument argument() {
            return Argument.of(getItem(1));
        }

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = t.consumeToken(",");
            r = r && Argument.parse(t, l + 1);
            t.exit(r);
            return r;
        }
    }
}

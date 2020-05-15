package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * conjunction: 'inversion' ('and' 'inversion')*
 */
public final class Conjunction extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("conjunction", RuleType.Conjunction, true);

    public static Conjunction of(ParseTreeNode node) {
        return new Conjunction(node);
    }

    private Conjunction(ParseTreeNode node) {
        super(RULE, node);
    }

    public Inversion inversion() {
        return Inversion.of(getItem(0));
    }

    public List<Conjunction2> conjunction2List() {
        return getList(1, Conjunction2::of);
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = Inversion.parse(t, l + 1);
        if (r) parseConjunction2List(t, l);
        t.exit(r);
        return r;
    }

    private static void parseConjunction2List(ParseTree t, int l) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!Conjunction2.parse(t, l + 1)) break;
            if (t.guardLoopExit(p)) break;
        }
        t.exitCollection();
    }

    /**
     * 'and' 'inversion'
     */
    public static final class Conjunction2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("conjunction:2", RuleType.Conjunction, false);

        public static Conjunction2 of(ParseTreeNode node) {
            return new Conjunction2(node);
        }

        private Conjunction2(ParseTreeNode node) {
            super(RULE, node);
        }

        public Inversion inversion() {
            return Inversion.of(getItem(1));
        }

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = t.consumeToken("and");
            r = r && Inversion.parse(t, l + 1);
            t.exit(r);
            return r;
        }
    }
}

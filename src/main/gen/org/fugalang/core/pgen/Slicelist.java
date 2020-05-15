package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * slicelist: 'slice' (',' 'slice')* [',']
 */
public final class Slicelist extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("slicelist", RuleType.Conjunction, true);

    public static Slicelist of(ParseTreeNode node) {
        return new Slicelist(node);
    }

    private Slicelist(ParseTreeNode node) {
        super(RULE, node);
    }

    public Slice slice() {
        return Slice.of(getItem(0));
    }

    public List<Slicelist2> slicelist2List() {
        return getList(1, Slicelist2::of);
    }

    public boolean isTokenComma() {
        return getBoolean(2);
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = Slice.parse(t, l + 1);
        if (r) parseSlicelist2List(t, l);
        if (r) t.consumeToken(",");
        t.exit(r);
        return r;
    }

    private static void parseSlicelist2List(ParseTree t, int l) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!Slicelist2.parse(t, l + 1)) break;
            if (t.guardLoopExit(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ',' 'slice'
     */
    public static final class Slicelist2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("slicelist:2", RuleType.Conjunction, false);

        public static Slicelist2 of(ParseTreeNode node) {
            return new Slicelist2(node);
        }

        private Slicelist2(ParseTreeNode node) {
            super(RULE, node);
        }

        public Slice slice() {
            return Slice.of(getItem(1));
        }

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = t.consumeToken(",");
            r = r && Slice.parse(t, l + 1);
            t.exit(r);
            return r;
        }
    }
}

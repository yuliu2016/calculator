package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * slicelist: 'slice' (',' 'slice')* [',']
 */
public final class Slicelist extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("slicelist", RuleType.Conjunction);

    public static Slicelist of(ParseTreeNode node) {
        return new Slicelist(node);
    }

    private Slicelist(ParseTreeNode node) {
        super(RULE, node);
    }

    public Slice slice() {
        return get(0, Slice::of);
    }

    public List<Slicelist2> slices() {
        return getList(1, Slicelist2::of);
    }

    public boolean isComma() {
        return is(2);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = Slice.parse(t, lv + 1);
        if (r) parseSlices(t, lv);
        if (r) t.consume(",");
        t.exit(r);
        return r;
    }

    private static void parseSlices(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!Slicelist2.parse(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ',' 'slice'
     */
    public static final class Slicelist2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("slicelist:2", RuleType.Conjunction);

        public static Slicelist2 of(ParseTreeNode node) {
            return new Slicelist2(node);
        }

        private Slicelist2(ParseTreeNode node) {
            super(RULE, node);
        }

        public Slice slice() {
            return get(1, Slice::of);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume(",");
            r = r && Slice.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}

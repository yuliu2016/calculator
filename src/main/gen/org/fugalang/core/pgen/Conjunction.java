package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * conjunction: 'inversion' ('and' 'inversion')*
 */
public final class Conjunction extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("conjunction", RuleType.Conjunction);

    public static Conjunction of(ParseTreeNode node) {
        return new Conjunction(node);
    }

    private Conjunction(ParseTreeNode node) {
        super(RULE, node);
    }

    public Inversion inversion() {
        return get(0, Inversion::of);
    }

    public List<Conjunction2> andInversions() {
        return getList(1, Conjunction2::of);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = Inversion.parse(t, lv + 1);
        if (r) parseAndInversions(t, lv);
        t.exit(r);
        return r;
    }

    private static void parseAndInversions(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!Conjunction2.parse(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * 'and' 'inversion'
     */
    public static final class Conjunction2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("conjunction:2", RuleType.Conjunction);

        public static Conjunction2 of(ParseTreeNode node) {
            return new Conjunction2(node);
        }

        private Conjunction2(ParseTreeNode node) {
            super(RULE, node);
        }

        public Inversion inversion() {
            return get(1, Inversion::of);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume("and");
            r = r && Inversion.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}

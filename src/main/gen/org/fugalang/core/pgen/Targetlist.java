package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * targetlist: 'target' (',' 'target')* [',']
 */
public final class Targetlist extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("targetlist", RuleType.Conjunction);

    public static Targetlist of(ParseTreeNode node) {
        return new Targetlist(node);
    }

    private Targetlist(ParseTreeNode node) {
        super(RULE, node);
    }

    public Target target() {
        return Target.of(get(0));
    }

    public List<Targetlist2> targetlist2List() {
        return getList(1, Targetlist2::of);
    }

    public boolean isComma() {
        return is(2);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = Target.parse(t, lv + 1);
        if (r) parseTargetlist2List(t, lv);
        if (r) t.consume(",");
        t.exit(r);
        return r;
    }

    private static void parseTargetlist2List(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!Targetlist2.parse(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ',' 'target'
     */
    public static final class Targetlist2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("targetlist:2", RuleType.Conjunction);

        public static Targetlist2 of(ParseTreeNode node) {
            return new Targetlist2(node);
        }

        private Targetlist2(ParseTreeNode node) {
            super(RULE, node);
        }

        public Target target() {
            return Target.of(get(1));
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume(",");
            r = r && Target.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}

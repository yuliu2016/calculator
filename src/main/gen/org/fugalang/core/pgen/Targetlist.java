package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * targetlist: 'target' (',' 'target')* [',']
 */
public final class Targetlist extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("targetlist", RuleType.Conjunction, true);

    public static Targetlist of(ParseTreeNode node) {
        return new Targetlist(node);
    }

    private Targetlist(ParseTreeNode node) {
        super(RULE, node);
    }

    public Target target() {
        return Target.of(getItem(0));
    }

    public List<Targetlist2> targetlist2List() {
        return getList(1, Targetlist2::of);
    }

    public boolean isTokenComma() {
        return getBoolean(2);
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = Target.parse(t, l + 1);
        if (r) parseTargetlist2List(t, l);
        if (r) t.consumeToken(",");
        t.exit(r);
        return r;
    }

    private static void parseTargetlist2List(ParseTree t, int l) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!Targetlist2.parse(t, l + 1)) break;
            if (t.guardLoopExit(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ',' 'target'
     */
    public static final class Targetlist2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("targetlist:2", RuleType.Conjunction, false);

        public static Targetlist2 of(ParseTreeNode node) {
            return new Targetlist2(node);
        }

        private Targetlist2(ParseTreeNode node) {
            super(RULE, node);
        }

        public Target target() {
            return Target.of(getItem(1));
        }

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = t.consumeToken(",");
            r = r && Target.parse(t, l + 1);
            t.exit(r);
            return r;
        }
    }
}

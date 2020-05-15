package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * dotted_as_names: 'dotted_as_name' (',' 'dotted_as_name')*
 */
public final class DottedAsNames extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("dotted_as_names", RuleType.Conjunction, true);

    public static DottedAsNames of(ParseTreeNode node) {
        return new DottedAsNames(node);
    }

    private DottedAsNames(ParseTreeNode node) {
        super(RULE, node);
    }

    public DottedAsName dottedAsName() {
        return DottedAsName.of(getItem(0));
    }

    public List<DottedAsNames2> dottedAsNames2List() {
        return getList(1, DottedAsNames2::of);
    }

    public static boolean parse(ParseTree t, int l) {
        if (!ParserUtil.recursionGuard(l, RULE)) return false;
        t.enter(l, RULE);
        boolean r;
        r = DottedAsName.parse(t, l + 1);
        if (r) parseDottedAsNames2List(t, l);
        t.exit(r);
        return r;
    }

    private static void parseDottedAsNames2List(ParseTree t, int l) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!DottedAsNames2.parse(t, l + 1)) break;
            if (t.guardLoopExit(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ',' 'dotted_as_name'
     */
    public static final class DottedAsNames2 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("dotted_as_names:2", RuleType.Conjunction, false);

        public static DottedAsNames2 of(ParseTreeNode node) {
            return new DottedAsNames2(node);
        }

        private DottedAsNames2(ParseTreeNode node) {
            super(RULE, node);
        }

        public DottedAsName dottedAsName() {
            return DottedAsName.of(getItem(1));
        }

        public static boolean parse(ParseTree t, int l) {
            if (!ParserUtil.recursionGuard(l, RULE)) return false;
            t.enter(l, RULE);
            boolean r;
            r = t.consumeToken(",");
            r = r && DottedAsName.parse(t, l + 1);
            t.exit(r);
            return r;
        }
    }
}

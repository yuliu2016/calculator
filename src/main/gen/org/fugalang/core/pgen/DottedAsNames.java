package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * dotted_as_names: 'dotted_as_name' (',' 'dotted_as_name')*
 */
public final class DottedAsNames extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("dotted_as_names", RuleType.Conjunction);

    public static DottedAsNames of(ParseTreeNode node) {
        return new DottedAsNames(node);
    }

    private DottedAsNames(ParseTreeNode node) {
        super(RULE, node);
    }

    public DottedAsName dottedAsName() {
        return get(0, DottedAsName::of);
    }

    public List<DottedAsNames2> dottedAsNames() {
        return getList(1, DottedAsNames2::of);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (t.recursionGuard(lv)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = DottedAsName.parse(t, lv + 1);
        if (r) parseDottedAsNames(t, lv);
        t.exit(r);
        return r;
    }

    private static void parseDottedAsNames(ParseTree t, int lv) {
        t.enterCollection();
        while (true) {
            var p = t.position();
            if (!DottedAsNames2.parse(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
    }

    /**
     * ',' 'dotted_as_name'
     */
    public static final class DottedAsNames2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("dotted_as_names:2", RuleType.Conjunction);

        public static DottedAsNames2 of(ParseTreeNode node) {
            return new DottedAsNames2(node);
        }

        private DottedAsNames2(ParseTreeNode node) {
            super(RULE, node);
        }

        public DottedAsName dottedAsName() {
            return get(1, DottedAsName::of);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (t.recursionGuard(lv)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = t.consume(",");
            r = r && DottedAsName.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}

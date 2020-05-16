package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * dict_or_set: '{' ['dict_maker' | 'set_maker'] '}'
 */
public final class DictOrSet extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("dict_or_set", RuleType.Conjunction);

    public static DictOrSet of(ParseTreeNode node) {
        return new DictOrSet(node);
    }

    private DictOrSet(ParseTreeNode node) {
        super(RULE, node);
    }

    public DictOrSet2 dictMakerOrSetMaker() {
        return DictOrSet2.of(get(1));
    }

    public boolean hasDictMakerOrSetMaker() {
        return has(1, DictOrSet2.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consume("{");
        if (r) DictOrSet2.parse(t, lv + 1);
        r = r && t.consume("}");
        t.exit(r);
        return r;
    }

    /**
     * 'dict_maker' | 'set_maker'
     */
    public static final class DictOrSet2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("dict_or_set:2", RuleType.Disjunction);

        public static DictOrSet2 of(ParseTreeNode node) {
            return new DictOrSet2(node);
        }

        private DictOrSet2(ParseTreeNode node) {
            super(RULE, node);
        }

        public DictMaker dictMaker() {
            return DictMaker.of(get(0));
        }

        public boolean hasDictMaker() {
            return has(0, DictMaker.RULE);
        }

        public SetMaker setMaker() {
            return SetMaker.of(get(1));
        }

        public boolean hasSetMaker() {
            return has(1, SetMaker.RULE);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = DictMaker.parse(t, lv + 1);
            r = r || SetMaker.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}

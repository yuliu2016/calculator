package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * dict_or_set: 'dict_maker' | 'set_maker'
 */
public final class DictOrSet extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("dict_or_set", RuleType.Disjunction);

    public static DictOrSet of(ParseTreeNode node) {
        return new DictOrSet(node);
    }

    private DictOrSet(ParseTreeNode node) {
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

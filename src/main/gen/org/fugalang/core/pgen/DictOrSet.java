package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * dict_or_set: 'dict_maker' | 'set_maker'
 */
public final class DictOrSet extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("dict_or_set", RuleType.Disjunction, true);

    public static DictOrSet of(ParseTreeNode node) {
        return new DictOrSet(node);
    }

    private DictOrSet(ParseTreeNode node) {
        super(RULE, node);
    }

    public DictMaker dictMaker() {
        return DictMaker.of(getItem(0));
    }

    public boolean hasDictMaker() {
        return hasItemOfRule(0, DictMaker.RULE);
    }

    public SetMaker setMaker() {
        return SetMaker.of(getItem(1));
    }

    public boolean hasSetMaker() {
        return hasItemOfRule(1, SetMaker.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = DictMaker.parse(parseTree, level + 1);
        result = result || SetMaker.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}

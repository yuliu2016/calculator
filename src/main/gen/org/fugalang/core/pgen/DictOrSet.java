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

    @Override
    protected void buildRule() {
        addChoice(dictMakerOrNull());
        addChoice(setMakerOrNull());
    }

    public DictMaker dictMaker() {
        var element = getItem(0);
        element.failIfAbsent(DictMaker.RULE);
        return DictMaker.of(element);
    }

    public DictMaker dictMakerOrNull() {
        var element = getItem(0);
        if (!element.isPresent(DictMaker.RULE)) {
            return null;
        }
        return DictMaker.of(element);
    }

    public boolean hasDictMaker() {
        var element = getItem(0);
        return element.isPresent(DictMaker.RULE);
    }

    public SetMaker setMaker() {
        var element = getItem(1);
        element.failIfAbsent(SetMaker.RULE);
        return SetMaker.of(element);
    }

    public SetMaker setMakerOrNull() {
        var element = getItem(1);
        if (!element.isPresent(SetMaker.RULE)) {
            return null;
        }
        return SetMaker.of(element);
    }

    public boolean hasSetMaker() {
        var element = getItem(1);
        return element.isPresent(SetMaker.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = DictMaker.parse(parseTree, level + 1);
        result = result || SetMaker.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}

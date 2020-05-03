package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * dictorsetmaker: 'dict_maker' | 'set_maker'
 */
public final class Dictorsetmaker extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("dictorsetmaker", RuleType.Disjunction, true);

    public static Dictorsetmaker of(ParseTreeNode node) {
        return new Dictorsetmaker(node);
    }

    private Dictorsetmaker(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addChoice(dictMaker());
        addChoice(setMaker());
    }

    public DictMaker dictMaker() {
        var element = getItem(0);
        if (!element.isPresent(DictMaker.RULE)) {
            return null;
        }
        return DictMaker.of(element);
    }

    public boolean hasDictMaker() {
        return dictMaker() != null;
    }

    public SetMaker setMaker() {
        var element = getItem(1);
        if (!element.isPresent(SetMaker.RULE)) {
            return null;
        }
        return SetMaker.of(element);
    }

    public boolean hasSetMaker() {
        return setMaker() != null;
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

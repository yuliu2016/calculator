package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * dictorsetmaker: 'dict_maker' | 'set_maker'
 */
public final class Dictorsetmaker extends DisjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("dictorsetmaker", RuleType.Disjunction, true);

    private final DictMaker dictMaker;
    private final SetMaker setMaker;

    public Dictorsetmaker(
            DictMaker dictMaker,
            SetMaker setMaker
    ) {
        this.dictMaker = dictMaker;
        this.setMaker = setMaker;
    }

    @Override
    protected void buildRule() {
        addChoice("dictMaker", dictMaker());
        addChoice("setMaker", setMaker());
    }

    public DictMaker dictMaker() {
        return dictMaker;
    }

    public boolean hasDictMaker() {
        return dictMaker() != null;
    }

    public SetMaker setMaker() {
        return setMaker;
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

package org.fugalang.core.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.DisjunctionRule;

// dictorsetmaker: 'dict_maker' | 'set_maker'
public final class Dictorsetmaker extends DisjunctionRule {
    public static final String RULE_NAME = "dictorsetmaker";

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
        setExplicitName(RULE_NAME);
        addChoice("dictMaker", dictMaker);
        addChoice("setMaker", setMaker);
    }

    public DictMaker dictMaker() {
        return dictMaker;
    }

    public SetMaker setMaker() {
        return setMaker;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        boolean result;

        result = DictMaker.parse(parseTree, level + 1);
        result = result || SetMaker.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }
}

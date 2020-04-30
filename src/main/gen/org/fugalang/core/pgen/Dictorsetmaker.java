package org.fugalang.core.pgen;

import org.fugalang.core.parser.DisjunctionRule;

// dictorsetmaker: 'dict_maker' | 'set_maker'
public final class Dictorsetmaker extends DisjunctionRule {
    private final DictMaker dictMaker;
    private final SetMaker setMaker;

    public Dictorsetmaker(
            DictMaker dictMaker,
            SetMaker setMaker
    ) {
        this.dictMaker = dictMaker;
        this.setMaker = setMaker;

        addChoice("dictMaker", dictMaker);
        addChoice("setMaker", setMaker);
    }

    public DictMaker getDictMaker() {
        return dictMaker;
    }

    public SetMaker getSetMaker() {
        return setMaker;
    }
}

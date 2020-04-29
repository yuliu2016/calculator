package org.fugalang.core.pgen;

// dictorsetmaker: 'dict_maker' | 'set_maker'
public class Dictorsetmaker {
    private final DictMaker dictMaker;
    private final SetMaker setMaker;

    public Dictorsetmaker(
            DictMaker dictMaker,
            SetMaker setMaker
    ) {
        this.dictMaker = dictMaker;
        this.setMaker = setMaker;
    }

    public DictMaker getDictMaker() {
        return dictMaker;
    }

    public SetMaker getSetMaker() {
        return setMaker;
    }
}

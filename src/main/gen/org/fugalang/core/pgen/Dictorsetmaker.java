package org.fugalang.core.pgen;

// dictorsetmaker: 'dict_maker' | 'set_maker'
public class Dictorsetmaker {
    public final DictMaker dictMaker;
    public final SetMaker setMaker;

    public Dictorsetmaker(
            DictMaker dictMaker,
            SetMaker setMaker
    ) {
        this.dictMaker = dictMaker;
        this.setMaker = setMaker;
    }
}

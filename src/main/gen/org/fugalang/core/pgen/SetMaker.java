package org.fugalang.core.pgen;

// set_maker: ('expr' | 'star_expr') ('comp_for' | (',' ('expr' | 'star_expr'))* [','])
public class SetMaker {
    public final SetMaker1Group setMaker1Group;
    public final SetMaker2Group setMaker2Group;

    public SetMaker(
            SetMaker1Group setMaker1Group,
            SetMaker2Group setMaker2Group
    ) {
        this.setMaker1Group = setMaker1Group;
        this.setMaker2Group = setMaker2Group;
    }
}

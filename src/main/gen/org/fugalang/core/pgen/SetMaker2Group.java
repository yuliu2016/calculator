package org.fugalang.core.pgen;

// 'comp_for' | (',' ('expr' | 'star_expr'))* [',']
public class SetMaker2Group {
    public final CompFor compFor;
    public final SetMaker2Group2 setMaker2Group2;

    public SetMaker2Group(
            CompFor compFor,
            SetMaker2Group2 setMaker2Group2
    ) {
        this.compFor = compFor;
        this.setMaker2Group2 = setMaker2Group2;
    }
}

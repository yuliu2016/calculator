package org.fugalang.core.pgen;

// varargslist: 'vfpdef' ['=' 'expr'] (',' 'vfpdef' ['=' 'expr'])*
public class Varargslist {
    public final Vfpdef vfpdef;
    public final Varargslist2Group varargslist2Group;
    public final Varargslist3Group varargslist3Group;

    public Varargslist(
            Vfpdef vfpdef,
            Varargslist2Group varargslist2Group,
            Varargslist3Group varargslist3Group
    ) {
        this.vfpdef = vfpdef;
        this.varargslist2Group = varargslist2Group;
        this.varargslist3Group = varargslist3Group;
    }
}

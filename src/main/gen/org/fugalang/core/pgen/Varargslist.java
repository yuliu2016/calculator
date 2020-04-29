package org.fugalang.core.pgen;

import java.util.List;

// varargslist: 'vfpdef' ['=' 'expr'] (',' 'vfpdef' ['=' 'expr'])*
public class Varargslist {
    public final Vfpdef vfpdef;
    public final Varargslist2Group varargslist2Group;
    public final List<Varargslist3Group> varargslist3GroupList;

    public Varargslist(
            Vfpdef vfpdef,
            Varargslist2Group varargslist2Group,
            List<Varargslist3Group> varargslist3GroupList
    ) {
        this.vfpdef = vfpdef;
        this.varargslist2Group = varargslist2Group;
        this.varargslist3GroupList = varargslist3GroupList;
    }
}

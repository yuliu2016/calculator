package org.fugalang.core.pgen;

// ',' 'vfpdef' ['=' 'expr']
public class Varargslist3Group {
    public final boolean isTokenComma;
    public final Vfpdef vfpdef;
    public final Varargslist3Group3Group varargslist3Group3Group;

    public Varargslist3Group(
            boolean isTokenComma,
            Vfpdef vfpdef,
            Varargslist3Group3Group varargslist3Group3Group
    ) {
        this.isTokenComma = isTokenComma;
        this.vfpdef = vfpdef;
        this.varargslist3Group3Group = varargslist3Group3Group;
    }
}

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

    // '=' 'expr'
    public static class Varargslist2Group {
        public final boolean isTokenAssign;
        public final Expr expr;

        public Varargslist2Group(
                boolean isTokenAssign,
                Expr expr
        ) {
            this.isTokenAssign = isTokenAssign;
            this.expr = expr;
        }
    }

    // ',' 'vfpdef' ['=' 'expr']
    public static class Varargslist3Group {
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

    // '=' 'expr'
    public static class Varargslist3Group3Group {
        public final boolean isTokenAssign;
        public final Expr expr;

        public Varargslist3Group3Group(
                boolean isTokenAssign,
                Expr expr
        ) {
            this.isTokenAssign = isTokenAssign;
            this.expr = expr;
        }
    }
}

package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;
import java.util.Optional;

// varargslist: 'vfpdef' ['=' 'expr'] (',' 'vfpdef' ['=' 'expr'])*
public final class Varargslist extends ConjunctionRule {
    private final Vfpdef vfpdef;
    private final Varargslist2Group varargslist2Group;
    private final List<Varargslist3Group> varargslist3GroupList;

    public Varargslist(
            Vfpdef vfpdef,
            Varargslist2Group varargslist2Group,
            List<Varargslist3Group> varargslist3GroupList
    ) {
        this.vfpdef = vfpdef;
        this.varargslist2Group = varargslist2Group;
        this.varargslist3GroupList = varargslist3GroupList;

        addRequired("vfpdef", vfpdef);
        addOptional("varargslist2Group", varargslist2Group);
        addRequired("varargslist3GroupList", varargslist3GroupList);
    }

    public Vfpdef getVfpdef() {
        return vfpdef;
    }

    public Optional<Varargslist2Group> getVarargslist2Group() {
        return Optional.ofNullable(varargslist2Group);
    }

    public List<Varargslist3Group> getVarargslist3GroupList() {
        return varargslist3GroupList;
    }

    // '=' 'expr'
    public static final class Varargslist2Group extends ConjunctionRule {
        private final boolean isTokenAssign;
        private final Expr expr;

        public Varargslist2Group(
                boolean isTokenAssign,
                Expr expr
        ) {
            this.isTokenAssign = isTokenAssign;
            this.expr = expr;

            addRequired("isTokenAssign", isTokenAssign);
            addRequired("expr", expr);
        }

        public boolean getIsTokenAssign() {
            return isTokenAssign;
        }

        public Expr getExpr() {
            return expr;
        }
    }

    // ',' 'vfpdef' ['=' 'expr']
    public static final class Varargslist3Group extends ConjunctionRule {
        private final boolean isTokenComma;
        private final Vfpdef vfpdef;
        private final Varargslist3Group3Group varargslist3Group3Group;

        public Varargslist3Group(
                boolean isTokenComma,
                Vfpdef vfpdef,
                Varargslist3Group3Group varargslist3Group3Group
        ) {
            this.isTokenComma = isTokenComma;
            this.vfpdef = vfpdef;
            this.varargslist3Group3Group = varargslist3Group3Group;

            addRequired("isTokenComma", isTokenComma);
            addRequired("vfpdef", vfpdef);
            addOptional("varargslist3Group3Group", varargslist3Group3Group);
        }

        public boolean getIsTokenComma() {
            return isTokenComma;
        }

        public Vfpdef getVfpdef() {
            return vfpdef;
        }

        public Optional<Varargslist3Group3Group> getVarargslist3Group3Group() {
            return Optional.ofNullable(varargslist3Group3Group);
        }
    }

    // '=' 'expr'
    public static final class Varargslist3Group3Group extends ConjunctionRule {
        private final boolean isTokenAssign;
        private final Expr expr;

        public Varargslist3Group3Group(
                boolean isTokenAssign,
                Expr expr
        ) {
            this.isTokenAssign = isTokenAssign;
            this.expr = expr;

            addRequired("isTokenAssign", isTokenAssign);
            addRequired("expr", expr);
        }

        public boolean getIsTokenAssign() {
            return isTokenAssign;
        }

        public Expr getExpr() {
            return expr;
        }
    }
}

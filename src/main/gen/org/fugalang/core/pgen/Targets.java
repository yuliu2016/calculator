package org.fugalang.core.pgen;

import java.util.List;

// targets: ('bitwise_or' | 'star_expr') (',' ('bitwise_or' | 'star_expr'))* [',']
public class Targets {
    private final Targets1Group targets1Group;
    private final List<Targets2Group> targets2GroupList;
    private final boolean isTokenComma;

    public Targets(
            Targets1Group targets1Group,
            List<Targets2Group> targets2GroupList,
            boolean isTokenComma
    ) {
        this.targets1Group = targets1Group;
        this.targets2GroupList = targets2GroupList;
        this.isTokenComma = isTokenComma;
    }

    public Targets1Group getTargets1Group() {
        return targets1Group;
    }

    public List<Targets2Group> getTargets2GroupList() {
        return targets2GroupList;
    }

    public boolean getIsTokenComma() {
        return isTokenComma;
    }

    // 'bitwise_or' | 'star_expr'
    public static class Targets1Group {
        private final BitwiseOr bitwiseOr;
        private final StarExpr starExpr;

        public Targets1Group(
                BitwiseOr bitwiseOr,
                StarExpr starExpr
        ) {
            this.bitwiseOr = bitwiseOr;
            this.starExpr = starExpr;
        }

        public BitwiseOr getBitwiseOr() {
            return bitwiseOr;
        }

        public StarExpr getStarExpr() {
            return starExpr;
        }
    }

    // ',' ('bitwise_or' | 'star_expr')
    public static class Targets2Group {
        private final boolean isTokenComma;
        private final Targets2Group2Group targets2Group2Group;

        public Targets2Group(
                boolean isTokenComma,
                Targets2Group2Group targets2Group2Group
        ) {
            this.isTokenComma = isTokenComma;
            this.targets2Group2Group = targets2Group2Group;
        }

        public boolean getIsTokenComma() {
            return isTokenComma;
        }

        public Targets2Group2Group getTargets2Group2Group() {
            return targets2Group2Group;
        }
    }

    // 'bitwise_or' | 'star_expr'
    public static class Targets2Group2Group {
        private final BitwiseOr bitwiseOr;
        private final StarExpr starExpr;

        public Targets2Group2Group(
                BitwiseOr bitwiseOr,
                StarExpr starExpr
        ) {
            this.bitwiseOr = bitwiseOr;
            this.starExpr = starExpr;
        }

        public BitwiseOr getBitwiseOr() {
            return bitwiseOr;
        }

        public StarExpr getStarExpr() {
            return starExpr;
        }
    }
}

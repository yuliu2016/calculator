package org.fugalang.core.pgen;

import java.util.List;

// targets: ('bitwise_or' | 'star_expr') (',' ('bitwise_or' | 'star_expr'))* [',']
public class Targets {
    public final Targets1Group targets1Group;
    public final List<Targets2Group> targets2GroupList;
    public final boolean isTokenComma;

    public Targets(
            Targets1Group targets1Group,
            List<Targets2Group> targets2GroupList,
            boolean isTokenComma
    ) {
        this.targets1Group = targets1Group;
        this.targets2GroupList = targets2GroupList;
        this.isTokenComma = isTokenComma;
    }

    // 'bitwise_or' | 'star_expr'
    public static class Targets1Group {
        public final BitwiseOr bitwiseOr;
        public final StarExpr starExpr;

        public Targets1Group(
                BitwiseOr bitwiseOr,
                StarExpr starExpr
        ) {
            this.bitwiseOr = bitwiseOr;
            this.starExpr = starExpr;
        }
    }

    // ',' ('bitwise_or' | 'star_expr')
    public static class Targets2Group {
        public final boolean isTokenComma;
        public final Targets2Group2Group targets2Group2Group;

        public Targets2Group(
                boolean isTokenComma,
                Targets2Group2Group targets2Group2Group
        ) {
            this.isTokenComma = isTokenComma;
            this.targets2Group2Group = targets2Group2Group;
        }
    }

    // 'bitwise_or' | 'star_expr'
    public static class Targets2Group2Group {
        public final BitwiseOr bitwiseOr;
        public final StarExpr starExpr;

        public Targets2Group2Group(
                BitwiseOr bitwiseOr,
                StarExpr starExpr
        ) {
            this.bitwiseOr = bitwiseOr;
            this.starExpr = starExpr;
        }
    }
}

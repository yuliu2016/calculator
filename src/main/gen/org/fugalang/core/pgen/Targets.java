package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;
import org.fugalang.core.parser.DisjunctionRule;

// targets: ('bitwise_or' | 'star_expr') (',' ('bitwise_or' | 'star_expr'))* [',']
public final class Targets extends ConjunctionRule {
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

        addRequired("targets1Group", targets1Group);
        addRequired("targets2GroupList", targets2GroupList);
        addRequired("isTokenComma", isTokenComma);
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
    public static final class Targets1Group extends DisjunctionRule {
        private final BitwiseOr bitwiseOr;
        private final StarExpr starExpr;

        public Targets1Group(
                BitwiseOr bitwiseOr,
                StarExpr starExpr
        ) {
            this.bitwiseOr = bitwiseOr;
            this.starExpr = starExpr;

            addChoice("bitwiseOr", bitwiseOr);
            addChoice("starExpr", starExpr);
        }

        public BitwiseOr getBitwiseOr() {
            return bitwiseOr;
        }

        public StarExpr getStarExpr() {
            return starExpr;
        }
    }

    // ',' ('bitwise_or' | 'star_expr')
    public static final class Targets2Group extends ConjunctionRule {
        private final boolean isTokenComma;
        private final Targets2Group2Group targets2Group2Group;

        public Targets2Group(
                boolean isTokenComma,
                Targets2Group2Group targets2Group2Group
        ) {
            this.isTokenComma = isTokenComma;
            this.targets2Group2Group = targets2Group2Group;

            addRequired("isTokenComma", isTokenComma);
            addRequired("targets2Group2Group", targets2Group2Group);
        }

        public boolean getIsTokenComma() {
            return isTokenComma;
        }

        public Targets2Group2Group getTargets2Group2Group() {
            return targets2Group2Group;
        }
    }

    // 'bitwise_or' | 'star_expr'
    public static final class Targets2Group2Group extends DisjunctionRule {
        private final BitwiseOr bitwiseOr;
        private final StarExpr starExpr;

        public Targets2Group2Group(
                BitwiseOr bitwiseOr,
                StarExpr starExpr
        ) {
            this.bitwiseOr = bitwiseOr;
            this.starExpr = starExpr;

            addChoice("bitwiseOr", bitwiseOr);
            addChoice("starExpr", starExpr);
        }

        public BitwiseOr getBitwiseOr() {
            return bitwiseOr;
        }

        public StarExpr getStarExpr() {
            return starExpr;
        }
    }
}

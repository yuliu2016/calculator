package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;
import org.fugalang.core.parser.DisjunctionRule;

// targets: ('bitwise_or' | 'star_expr') (',' ('bitwise_or' | 'star_expr'))* [',']
public final class Targets extends ConjunctionRule {
    private final Targets1 targets1;
    private final List<Targets2> targets2List;
    private final boolean isTokenComma;

    public Targets(
            Targets1 targets1,
            List<Targets2> targets2List,
            boolean isTokenComma
    ) {
        this.targets1 = targets1;
        this.targets2List = targets2List;
        this.isTokenComma = isTokenComma;
    }

    @Override
    protected void buildRule() {
        addRequired("targets1", targets1);
        addRequired("targets2List", targets2List);
        addRequired("isTokenComma", isTokenComma);
    }

    public Targets1 targets1() {
        return targets1;
    }

    public List<Targets2> targets2List() {
        return targets2List;
    }

    public boolean isTokenComma() {
        return isTokenComma;
    }

    // 'bitwise_or' | 'star_expr'
    public static final class Targets1 extends DisjunctionRule {
        private final BitwiseOr bitwiseOr;
        private final StarExpr starExpr;

        public Targets1(
                BitwiseOr bitwiseOr,
                StarExpr starExpr
        ) {
            this.bitwiseOr = bitwiseOr;
            this.starExpr = starExpr;
        }

        @Override
        protected void buildRule() {
            addChoice("bitwiseOr", bitwiseOr);
            addChoice("starExpr", starExpr);
        }

        public BitwiseOr bitwiseOr() {
            return bitwiseOr;
        }

        public StarExpr starExpr() {
            return starExpr;
        }
    }

    // ',' ('bitwise_or' | 'star_expr')
    public static final class Targets2 extends ConjunctionRule {
        private final boolean isTokenComma;
        private final Targets22 targets22;

        public Targets2(
                boolean isTokenComma,
                Targets22 targets22
        ) {
            this.isTokenComma = isTokenComma;
            this.targets22 = targets22;
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenComma", isTokenComma);
            addRequired("targets22", targets22);
        }

        public boolean isTokenComma() {
            return isTokenComma;
        }

        public Targets22 targets22() {
            return targets22;
        }
    }

    // 'bitwise_or' | 'star_expr'
    public static final class Targets22 extends DisjunctionRule {
        private final BitwiseOr bitwiseOr;
        private final StarExpr starExpr;

        public Targets22(
                BitwiseOr bitwiseOr,
                StarExpr starExpr
        ) {
            this.bitwiseOr = bitwiseOr;
            this.starExpr = starExpr;
        }

        @Override
        protected void buildRule() {
            addChoice("bitwiseOr", bitwiseOr);
            addChoice("starExpr", starExpr);
        }

        public BitwiseOr bitwiseOr() {
            return bitwiseOr;
        }

        public StarExpr starExpr() {
            return starExpr;
        }
    }
}

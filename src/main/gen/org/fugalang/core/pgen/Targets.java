package org.fugalang.core.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;
import org.fugalang.core.parser.DisjunctionRule;

// targets: ('bitwise_or' | 'star_expr') (',' ('bitwise_or' | 'star_expr'))* [',']
public final class Targets extends ConjunctionRule {
    public static final String RULE_NAME = "targets";

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
        setExplicitName(RULE_NAME);
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

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        boolean result;

        result = Targets1.parse(parseTree, level + 1);
        parseTree.enterCollection();
        while (true) {
            if (!Targets2.parse(parseTree, level + 1)) {
                break;
            }
        }
        parseTree.exitCollection();
        result = result && parseTree.consumeTokenLiteral(",");

        parseTree.exit(level, marker, result);
        return result;
    }

    // 'bitwise_or' | 'star_expr'
    public static final class Targets1 extends DisjunctionRule {
        public static final String RULE_NAME = "targets:1";

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
            setImpliedName(RULE_NAME);
            addChoice("bitwiseOr", bitwiseOr);
            addChoice("starExpr", starExpr);
        }

        public BitwiseOr bitwiseOr() {
            return bitwiseOr;
        }

        public StarExpr starExpr() {
            return starExpr;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            result = BitwiseOr.parse(parseTree, level + 1);
            if (!result) result = StarExpr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    // ',' ('bitwise_or' | 'star_expr')
    public static final class Targets2 extends ConjunctionRule {
        public static final String RULE_NAME = "targets:2";

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
            setImpliedName(RULE_NAME);
            addRequired("isTokenComma", isTokenComma);
            addRequired("targets22", targets22);
        }

        public boolean isTokenComma() {
            return isTokenComma;
        }

        public Targets22 targets22() {
            return targets22;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            result = parseTree.consumeTokenLiteral(",");
            result = result && Targets22.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    // 'bitwise_or' | 'star_expr'
    public static final class Targets22 extends DisjunctionRule {
        public static final String RULE_NAME = "targets:2:2";

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
            setImpliedName(RULE_NAME);
            addChoice("bitwiseOr", bitwiseOr);
            addChoice("starExpr", starExpr);
        }

        public BitwiseOr bitwiseOr() {
            return bitwiseOr;
        }

        public StarExpr starExpr() {
            return starExpr;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            result = BitwiseOr.parse(parseTree, level + 1);
            if (!result) result = StarExpr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}

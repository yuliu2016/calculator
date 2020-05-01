package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import org.fugalang.core.parser.DisjunctionRule;
import org.fugalang.core.parser.ParseTree;

/**
 * comp_op: '<' | '>' | '==' | '>=' | '<=' | '!=' | 'in' | 'not' 'in' | 'is' | 'is' 'not'
 */
public final class CompOp extends DisjunctionRule {
    public static final String RULE_NAME = "comp_op";

    private final boolean isTokenLess;
    private final boolean isTokenGreater;
    private final boolean isTokenEqual;
    private final boolean isTokenMoreEqual;
    private final boolean isTokenLessEqual;
    private final boolean isTokenNequal;
    private final boolean isTokenIn;
    private final CompOp8 compOp8;
    private final boolean isTokenIs;
    private final CompOp10 compOp10;

    public CompOp(
            boolean isTokenLess,
            boolean isTokenGreater,
            boolean isTokenEqual,
            boolean isTokenMoreEqual,
            boolean isTokenLessEqual,
            boolean isTokenNequal,
            boolean isTokenIn,
            CompOp8 compOp8,
            boolean isTokenIs,
            CompOp10 compOp10
    ) {
        this.isTokenLess = isTokenLess;
        this.isTokenGreater = isTokenGreater;
        this.isTokenEqual = isTokenEqual;
        this.isTokenMoreEqual = isTokenMoreEqual;
        this.isTokenLessEqual = isTokenLessEqual;
        this.isTokenNequal = isTokenNequal;
        this.isTokenIn = isTokenIn;
        this.compOp8 = compOp8;
        this.isTokenIs = isTokenIs;
        this.compOp10 = compOp10;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addChoice("isTokenLess", isTokenLess);
        addChoice("isTokenGreater", isTokenGreater);
        addChoice("isTokenEqual", isTokenEqual);
        addChoice("isTokenMoreEqual", isTokenMoreEqual);
        addChoice("isTokenLessEqual", isTokenLessEqual);
        addChoice("isTokenNequal", isTokenNequal);
        addChoice("isTokenIn", isTokenIn);
        addChoice("compOp8", compOp8);
        addChoice("isTokenIs", isTokenIs);
        addChoice("compOp10", compOp10);
    }

    public boolean isTokenLess() {
        return isTokenLess;
    }

    public boolean isTokenGreater() {
        return isTokenGreater;
    }

    public boolean isTokenEqual() {
        return isTokenEqual;
    }

    public boolean isTokenMoreEqual() {
        return isTokenMoreEqual;
    }

    public boolean isTokenLessEqual() {
        return isTokenLessEqual;
    }

    public boolean isTokenNequal() {
        return isTokenNequal;
    }

    public boolean isTokenIn() {
        return isTokenIn;
    }

    public CompOp8 compOp8() {
        return compOp8;
    }

    public boolean hasCompOp8() {
        return compOp8() != null;
    }

    public boolean isTokenIs() {
        return isTokenIs;
    }

    public CompOp10 compOp10() {
        return compOp10;
    }

    public boolean hasCompOp10() {
        return compOp10() != null;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        boolean result;

        result = parseTree.consumeTokenLiteral("<");
        result = result || parseTree.consumeTokenLiteral(">");
        result = result || parseTree.consumeTokenLiteral("==");
        result = result || parseTree.consumeTokenLiteral(">=");
        result = result || parseTree.consumeTokenLiteral("<=");
        result = result || parseTree.consumeTokenLiteral("!=");
        result = result || parseTree.consumeTokenLiteral("in");
        result = result || CompOp8.parse(parseTree, level + 1);
        result = result || parseTree.consumeTokenLiteral("is");
        result = result || CompOp10.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * 'not' 'in'
     */
    public static final class CompOp8 extends ConjunctionRule {
        public static final String RULE_NAME = "comp_op:8";

        private final boolean isTokenNot;
        private final boolean isTokenIn;

        public CompOp8(
                boolean isTokenNot,
                boolean isTokenIn
        ) {
            this.isTokenNot = isTokenNot;
            this.isTokenIn = isTokenIn;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("isTokenNot", isTokenNot);
            addRequired("isTokenIn", isTokenIn);
        }

        public boolean isTokenNot() {
            return isTokenNot;
        }

        public boolean isTokenIn() {
            return isTokenIn;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            result = parseTree.consumeTokenLiteral("not");
            result = result && parseTree.consumeTokenLiteral("in");

            parseTree.exit(level, marker, result);
            return result;
        }
    }

    /**
     * 'is' 'not'
     */
    public static final class CompOp10 extends ConjunctionRule {
        public static final String RULE_NAME = "comp_op:10";

        private final boolean isTokenIs;
        private final boolean isTokenNot;

        public CompOp10(
                boolean isTokenIs,
                boolean isTokenNot
        ) {
            this.isTokenIs = isTokenIs;
            this.isTokenNot = isTokenNot;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("isTokenIs", isTokenIs);
            addRequired("isTokenNot", isTokenNot);
        }

        public boolean isTokenIs() {
            return isTokenIs;
        }

        public boolean isTokenNot() {
            return isTokenNot;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            result = parseTree.consumeTokenLiteral("is");
            result = result && parseTree.consumeTokenLiteral("not");

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}

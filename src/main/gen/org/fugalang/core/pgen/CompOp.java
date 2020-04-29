package org.fugalang.core.pgen;

// comp_op: '<' | '>' | '==' | '>=' | '<=' | '!=' | 'in' | 'not' 'in' | 'is' | 'is' 'not'
public class CompOp {
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

    public boolean getIsTokenLess() {
        return isTokenLess;
    }

    public boolean getIsTokenGreater() {
        return isTokenGreater;
    }

    public boolean getIsTokenEqual() {
        return isTokenEqual;
    }

    public boolean getIsTokenMoreEqual() {
        return isTokenMoreEqual;
    }

    public boolean getIsTokenLessEqual() {
        return isTokenLessEqual;
    }

    public boolean getIsTokenNequal() {
        return isTokenNequal;
    }

    public boolean getIsTokenIn() {
        return isTokenIn;
    }

    public CompOp8 getCompOp8() {
        return compOp8;
    }

    public boolean getIsTokenIs() {
        return isTokenIs;
    }

    public CompOp10 getCompOp10() {
        return compOp10;
    }

    // 'not' 'in'
    public static class CompOp8 {
        private final boolean isTokenNot;
        private final boolean isTokenIn;

        public CompOp8(
                boolean isTokenNot,
                boolean isTokenIn
        ) {
            this.isTokenNot = isTokenNot;
            this.isTokenIn = isTokenIn;
        }

        public boolean getIsTokenNot() {
            return isTokenNot;
        }

        public boolean getIsTokenIn() {
            return isTokenIn;
        }
    }

    // 'is' 'not'
    public static class CompOp10 {
        private final boolean isTokenIs;
        private final boolean isTokenNot;

        public CompOp10(
                boolean isTokenIs,
                boolean isTokenNot
        ) {
            this.isTokenIs = isTokenIs;
            this.isTokenNot = isTokenNot;
        }

        public boolean getIsTokenIs() {
            return isTokenIs;
        }

        public boolean getIsTokenNot() {
            return isTokenNot;
        }
    }
}

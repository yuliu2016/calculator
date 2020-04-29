package org.fugalang.core.pgen;

// comp_op: '<' | '>' | '==' | '>=' | '<=' | '!=' | 'in' | 'not' 'in' | 'is' | 'is' 'not'
public class CompOp {
    public final boolean isTokenLess;
    public final boolean isTokenGreater;
    public final boolean isTokenEqual;
    public final boolean isTokenMoreEqual;
    public final boolean isTokenLessEqual;
    public final boolean isTokenNequal;
    public final boolean isTokenIn;
    public final CompOp8 compOp8;
    public final boolean isTokenIs;
    public final CompOp10 compOp10;

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

    // 'not' 'in'
    public static class CompOp8 {
        public final boolean isTokenNot;
        public final boolean isTokenIn;

        public CompOp8(
                boolean isTokenNot,
                boolean isTokenIn
        ) {
            this.isTokenNot = isTokenNot;
            this.isTokenIn = isTokenIn;
        }
    }

    // 'is' 'not'
    public static class CompOp10 {
        public final boolean isTokenIs;
        public final boolean isTokenNot;

        public CompOp10(
                boolean isTokenIs,
                boolean isTokenNot
        ) {
            this.isTokenIs = isTokenIs;
            this.isTokenNot = isTokenNot;
        }
    }
}

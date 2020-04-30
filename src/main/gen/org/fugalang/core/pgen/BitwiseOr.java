package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// bitwise_or: 'bitwise_xor' ('|' 'bitwise_xor')*
public final class BitwiseOr extends ConjunctionRule {
    private final BitwiseXor bitwiseXor;
    private final List<BitwiseOr2> bitwiseOr2List;

    public BitwiseOr(
            BitwiseXor bitwiseXor,
            List<BitwiseOr2> bitwiseOr2List
    ) {
        this.bitwiseXor = bitwiseXor;
        this.bitwiseOr2List = bitwiseOr2List;
    }

    @Override
    protected void buildRule() {
        addRequired("bitwiseXor", bitwiseXor);
        addRequired("bitwiseOr2List", bitwiseOr2List);
    }

    public BitwiseXor bitwiseXor() {
        return bitwiseXor;
    }

    public List<BitwiseOr2> bitwiseOr2List() {
        return bitwiseOr2List;
    }

    // '|' 'bitwise_xor'
    public static final class BitwiseOr2 extends ConjunctionRule {
        private final boolean isTokenBitOr;
        private final BitwiseXor bitwiseXor;

        public BitwiseOr2(
                boolean isTokenBitOr,
                BitwiseXor bitwiseXor
        ) {
            this.isTokenBitOr = isTokenBitOr;
            this.bitwiseXor = bitwiseXor;
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenBitOr", isTokenBitOr);
            addRequired("bitwiseXor", bitwiseXor);
        }

        public boolean isTokenBitOr() {
            return isTokenBitOr;
        }

        public BitwiseXor bitwiseXor() {
            return bitwiseXor;
        }
    }
}

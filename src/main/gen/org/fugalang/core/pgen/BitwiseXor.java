package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// bitwise_xor: 'bitwise_and' ('^' 'bitwise_and')*
public final class BitwiseXor extends ConjunctionRule {
    private final BitwiseAnd bitwiseAnd;
    private final List<BitwiseXor2> bitwiseXor2List;

    public BitwiseXor(
            BitwiseAnd bitwiseAnd,
            List<BitwiseXor2> bitwiseXor2List
    ) {
        this.bitwiseAnd = bitwiseAnd;
        this.bitwiseXor2List = bitwiseXor2List;
    }

    @Override
    protected void buildRule() {
        addRequired("bitwiseAnd", bitwiseAnd);
        addRequired("bitwiseXor2List", bitwiseXor2List);
    }

    public BitwiseAnd bitwiseAnd() {
        return bitwiseAnd;
    }

    public List<BitwiseXor2> bitwiseXor2List() {
        return bitwiseXor2List;
    }

    // '^' 'bitwise_and'
    public static final class BitwiseXor2 extends ConjunctionRule {
        private final boolean isTokenBitXor;
        private final BitwiseAnd bitwiseAnd;

        public BitwiseXor2(
                boolean isTokenBitXor,
                BitwiseAnd bitwiseAnd
        ) {
            this.isTokenBitXor = isTokenBitXor;
            this.bitwiseAnd = bitwiseAnd;
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenBitXor", isTokenBitXor);
            addRequired("bitwiseAnd", bitwiseAnd);
        }

        public boolean isTokenBitXor() {
            return isTokenBitXor;
        }

        public BitwiseAnd bitwiseAnd() {
            return bitwiseAnd;
        }
    }
}

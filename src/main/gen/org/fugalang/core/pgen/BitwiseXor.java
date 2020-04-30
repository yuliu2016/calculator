package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// bitwise_xor: 'bitwise_and' ('^' 'bitwise_and')*
public final class BitwiseXor extends ConjunctionRule {
    private final BitwiseAnd bitwiseAnd;
    private final List<BitwiseXor2Group> bitwiseXor2GroupList;

    public BitwiseXor(
            BitwiseAnd bitwiseAnd,
            List<BitwiseXor2Group> bitwiseXor2GroupList
    ) {
        this.bitwiseAnd = bitwiseAnd;
        this.bitwiseXor2GroupList = bitwiseXor2GroupList;

        addRequired("bitwiseAnd", bitwiseAnd);
        addRequired("bitwiseXor2GroupList", bitwiseXor2GroupList);
    }

    public BitwiseAnd bitwiseAnd() {
        return bitwiseAnd;
    }

    public List<BitwiseXor2Group> bitwiseXor2GroupList() {
        return bitwiseXor2GroupList;
    }

    // '^' 'bitwise_and'
    public static final class BitwiseXor2Group extends ConjunctionRule {
        private final boolean isTokenBitXor;
        private final BitwiseAnd bitwiseAnd;

        public BitwiseXor2Group(
                boolean isTokenBitXor,
                BitwiseAnd bitwiseAnd
        ) {
            this.isTokenBitXor = isTokenBitXor;
            this.bitwiseAnd = bitwiseAnd;

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

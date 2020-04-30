package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// bitwise_or: 'bitwise_xor' ('|' 'bitwise_xor')*
public final class BitwiseOr extends ConjunctionRule {
    private final BitwiseXor bitwiseXor;
    private final List<BitwiseOr2Group> bitwiseOr2GroupList;

    public BitwiseOr(
            BitwiseXor bitwiseXor,
            List<BitwiseOr2Group> bitwiseOr2GroupList
    ) {
        this.bitwiseXor = bitwiseXor;
        this.bitwiseOr2GroupList = bitwiseOr2GroupList;

        addRequired("bitwiseXor", bitwiseXor);
        addRequired("bitwiseOr2GroupList", bitwiseOr2GroupList);
    }

    public BitwiseXor bitwiseXor() {
        return bitwiseXor;
    }

    public List<BitwiseOr2Group> bitwiseOr2GroupList() {
        return bitwiseOr2GroupList;
    }

    // '|' 'bitwise_xor'
    public static final class BitwiseOr2Group extends ConjunctionRule {
        private final boolean isTokenBitOr;
        private final BitwiseXor bitwiseXor;

        public BitwiseOr2Group(
                boolean isTokenBitOr,
                BitwiseXor bitwiseXor
        ) {
            this.isTokenBitOr = isTokenBitOr;
            this.bitwiseXor = bitwiseXor;

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

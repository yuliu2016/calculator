package org.fugalang.core.pgen;

import java.util.List;

// bitwise_or: 'bitwise_xor' ('|' 'bitwise_xor')*
public class BitwiseOr {
    public final BitwiseXor bitwiseXor;
    public final List<BitwiseOr2Group> bitwiseOr2GroupList;

    public BitwiseOr(
            BitwiseXor bitwiseXor,
            List<BitwiseOr2Group> bitwiseOr2GroupList
    ) {
        this.bitwiseXor = bitwiseXor;
        this.bitwiseOr2GroupList = bitwiseOr2GroupList;
    }

    // '|' 'bitwise_xor'
    public static class BitwiseOr2Group {
        public final boolean isTokenBitOr;
        public final BitwiseXor bitwiseXor;

        public BitwiseOr2Group(
                boolean isTokenBitOr,
                BitwiseXor bitwiseXor
        ) {
            this.isTokenBitOr = isTokenBitOr;
            this.bitwiseXor = bitwiseXor;
        }
    }
}

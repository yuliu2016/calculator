package org.fugalang.core.pgen;

import java.util.List;

// bitwise_xor: 'bitwise_and' ('^' 'bitwise_and')*
public class BitwiseXor {
    public final BitwiseAnd bitwiseAnd;
    public final List<BitwiseXor2Group> bitwiseXor2GroupList;

    public BitwiseXor(
            BitwiseAnd bitwiseAnd,
            List<BitwiseXor2Group> bitwiseXor2GroupList
    ) {
        this.bitwiseAnd = bitwiseAnd;
        this.bitwiseXor2GroupList = bitwiseXor2GroupList;
    }

    // '^' 'bitwise_and'
    public static class BitwiseXor2Group {
        public final boolean isTokenBitXor;
        public final BitwiseAnd bitwiseAnd;

        public BitwiseXor2Group(
                boolean isTokenBitXor,
                BitwiseAnd bitwiseAnd
        ) {
            this.isTokenBitXor = isTokenBitXor;
            this.bitwiseAnd = bitwiseAnd;
        }
    }
}

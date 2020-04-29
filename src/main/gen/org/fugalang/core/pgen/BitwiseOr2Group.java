package org.fugalang.core.pgen;

// '|' 'bitwise_xor'
public class BitwiseOr2Group {
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

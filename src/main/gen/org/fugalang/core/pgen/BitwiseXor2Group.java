package org.fugalang.core.pgen;

// '^' 'bitwise_and'
public class BitwiseXor2Group {
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

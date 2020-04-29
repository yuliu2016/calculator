package org.fugalang.core.pgen;

// bitwise_xor: 'bitwise_and' ('^' 'bitwise_and')*
public class BitwiseXor {
    public final BitwiseAnd bitwiseAnd;
    public final BitwiseXor2Group bitwiseXor2Group;

    public BitwiseXor(
            BitwiseAnd bitwiseAnd,
            BitwiseXor2Group bitwiseXor2Group
    ) {
        this.bitwiseAnd = bitwiseAnd;
        this.bitwiseXor2Group = bitwiseXor2Group;
    }
}

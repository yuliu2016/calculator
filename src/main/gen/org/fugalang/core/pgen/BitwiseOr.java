package org.fugalang.core.pgen;

// bitwise_or: 'bitwise_xor' ('|' 'bitwise_xor')*
public class BitwiseOr {
    public final BitwiseXor bitwiseXor;
    public final BitwiseOr2Group bitwiseOr2Group;

    public BitwiseOr(
            BitwiseXor bitwiseXor,
            BitwiseOr2Group bitwiseOr2Group
    ) {
        this.bitwiseXor = bitwiseXor;
        this.bitwiseOr2Group = bitwiseOr2Group;
    }
}

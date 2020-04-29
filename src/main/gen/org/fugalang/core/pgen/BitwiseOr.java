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
}

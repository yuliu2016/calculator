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
}

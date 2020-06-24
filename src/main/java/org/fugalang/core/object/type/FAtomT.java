package org.fugalang.core.object.type;

import org.fugalang.core.object.call.FCallable;

public class FAtomT {
    final FCallable[] binary_func;
    final FCallable[] rh_binary_func;
    final FCallable[] inplace_binary_func;
    final FCallable[] unary_func;
    final FCallable[] compare_func;
    final FCallable hash_func;
    final FCallable bool_func;

    public FAtomT(
            FCallable[] binary_func,
            FCallable[] rh_binary_func,
            FCallable[] inplace_binary_func,
            FCallable[] unary_func,
            FCallable[] compare_func,
            FCallable hash_func,
            FCallable bool_func) {
        this.binary_func = binary_func;
        this.rh_binary_func = rh_binary_func;
        this.inplace_binary_func = inplace_binary_func;
        this.unary_func = unary_func;
        this.compare_func = compare_func;
        this.hash_func = hash_func;
        this.bool_func = bool_func;
    }
}

package org.fugalang.core.object.type;

import org.fugalang.core.object.call.FCallable;

public class FStateT {
    public final FCallable ctx_enter;
    public final FCallable ctx_exit;
    public final FCallable iter_next;

    public FStateT(FCallable ctx_enter, FCallable ctx_exit, FCallable iter_next) {
        this.ctx_enter = ctx_enter;
        this.ctx_exit = ctx_exit;
        this.iter_next = iter_next;
    }
}

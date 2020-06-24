package org.fugalang.core.object.type;

import org.fugalang.core.object.call.FCallable;

public class FSequenceT {
    public final FCallable get_item;
    public final FCallable set_item;
    public final FCallable length;
    public final FCallable iterator;
    public final FCallable reversed;
    public final FCallable contains;

    public FSequenceT(FCallable get_item,
                      FCallable set_item,
                      FCallable length,
                      FCallable iterator,
                      FCallable reversed,
                      FCallable contains) {
        this.get_item = get_item;
        this.set_item = set_item;
        this.length = length;
        this.iterator = iterator;
        this.reversed = reversed;
        this.contains = contains;
    }
}

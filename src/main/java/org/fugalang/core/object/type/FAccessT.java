package org.fugalang.core.object.type;

import org.fugalang.core.object.call.FCallable;

public class FAccessT {
    final FCallable get_attr;
    final FCallable find_attr;
    final FCallable set_attr;
    final FCallable del_attr;
    final FCallable dir;

    public FAccessT(
            FCallable get_attr,
            FCallable find_attr,
            FCallable set_attr,
            FCallable del_attr,
            FCallable dir) {
        this.get_attr = get_attr;
        this.find_attr = find_attr;
        this.set_attr = set_attr;
        this.del_attr = del_attr;
        this.dir = dir;
    }
}

package org.fugalang.core.call;

import java.util.Map;

@SuppressWarnings("unused")
@FunctionalInterface
public interface FCallable {
    Object call(Object args, Object kwargs);

    default Object callNoArgs() {
        return call(null, null);
    }

    default Object callWithArray(Object... arr) {
        return call(arr, null);
    }

    default Object callWithKw(Object[] arr, Map<String, Object> map) {
        return call(arr, map);
    }
}

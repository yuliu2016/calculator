package org.fugalang.core.object.call;

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

    default Object callOneArg(Object arg) {
        return callWithArray(arg);
    }

    default Object callTwoArgs(Object arg1, Object arg2) {
        return callWithArray(arg1, arg2);
    }

    default Object callThreeArgs(Object arg1, Object arg2, Object arg3) {
        return callWithArray(arg1, arg2, arg3);
    }

    default Object callWithKw(Object[] arr, Map<String, Object> map) {
        return call(arr, map);
    }
}

package org.fugalang.core.object.type;

public class FProxyType<T> implements FType<T> {
    private final FMetaType metaType;

    public FProxyType(FMetaType metaType) {
        this.metaType = metaType;
    }

    @Override
    public FMetaType metaType() {
        return metaType;
    }

    @Override
    public Object getattr(T a, Object o) {
        return null;
    }

    @Override
    public Object setattr(T a, Object o, Object v) {
        return null;
    }

    @Override
    public Object length(T a) {
        return null;
    }

    @Override
    public Object get(T a, Object o) {
        return null;
    }

    @Override
    public Object set(T a, Object o, Object v) {
        return null;
    }

    @Override
    public Object del(T a, Object o) {
        return null;
    }

    @Override
    public Object iterator(T a) {
        return null;
    }

    @Override
    public Object reversed(T a) {
        return null;
    }

    @Override
    public Object contains(T a, Object o) {
        return null;
    }

    @Override
    public Object compare_op(T a, Object o, int compare_op) {
        var slot_func = metaType.compare_func(compare_op);
        if (slot_func == null) {
            return null;
        }
        return slot_func.callTwoArgs(a, o);
    }

    @Override
    public Object unary_op(T a, int unary_op) {
        var slot_func = metaType.unary_func(unary_op);
        if (slot_func == null) {
            return null;
        }
        return slot_func.callOneArg(a);
    }

    @Override
    public Object binary_op(T a, Object o, int binary_op) {
        var slot_func = metaType.binary_func(binary_op);
        if (slot_func == null) {
            return null;
        }
        return slot_func.callTwoArgs(a, o);
    }

    @Override
    public Object rh_binary_op(T a, Object o, int binary_op) {
        var slot_func = metaType.rh_binary_func(binary_op);
        if (slot_func == null) {
            return null;
        }
        return slot_func.callTwoArgs(a, o);
    }

    @Override
    public Object inplace_binary_op(T a, Object o, int binary_op) {
        var slot_func = metaType.inplace_binary_func(binary_op);
        if (slot_func == null) {
            return null;
        }
        return slot_func.callTwoArgs(a, o);
    }

}

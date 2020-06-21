package org.fugalang.core.object;

import org.fugalang.core.object.type.FMetaType;
import org.fugalang.core.object.type.FType;

public class FString implements FType<String> {

    static FString INSTANCE = new FString();

    private FString() {
    }

    @Override
    public Object compare_op(String a, Object o, int compare_op) {
        return null;
    }

    @Override
    public Object getattr(String s, Object o) {
        return null;
    }

    @Override
    public Object setattr(String s, Object o, Object v) {
        return null;
    }

    @Override
    public Object delattr(String s, Object o) {
        return null;
    }

    @Override
    public Object length(String s) {
        return null;
    }

    @Override
    public Object get(String s, Object o) {
        return null;
    }

    @Override
    public Object set(String s, Object o, Object v) {
        return null;
    }

    @Override
    public Object del(String s, Object o) {
        return null;
    }

    @Override
    public Object iterator(String s) {
        return null;
    }

    @Override
    public Object reversed(String s) {
        return null;
    }

    @Override
    public Object unary_op(String a, int unary_op) {
        return null;
    }

    @Override
    public Object binary_op(String a, Object o, int binary_op) {
        return null;
    }

    @Override
    public Object rh_binary_op(String a, Object b, int binary_op) {
        return null;
    }

    @Override
    public Object inplace_binary_op(String a, Object b, int binary_op) {
        return null;
    }

    @Override
    public Object context_enter(String s) {
        return null;
    }

    @Override
    public Object context_exit(String s, Object o) {
        return null;
    }

    @Override
    public FMetaType meta() {
        return null;
    }
}

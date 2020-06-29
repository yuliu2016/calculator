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
    public Object get(String s, int index) {
        return null;
    }

    @Override
    public Object set(String s, int index, Object v) {
        return null;
    }

    @Override
    public Object contains(String s, Object o) {
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
    public Object rh_binary_op(String a, Object o, int binary_op) {
        return null;
    }

    @Override
    public Object inplace_binary_op(String a, Object o, int binary_op) {
        return null;
    }

    @Override
    public FMetaType metaType() {
        return null;
    }
}

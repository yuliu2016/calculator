package org.fugalang.core.object;

import org.fugalang.core.object.type.FMetaType;
import org.fugalang.core.object.type.FType;

import java.util.Set;

public final class FSet implements FType<Set<Object>> {

    static FSet INSTANCE = new FSet();

    private FSet() {

    }

    @Override
    public Object compare_op(Set<Object> a, Object o, int compare_op) {
        return null;
    }

    @Override
    public Object get(Set<Object> a, int index) {
        return null;
    }

    @Override
    public Object set(Set<Object> a, int index, Object v) {
        return null;
    }

    @Override
    public Object contains(Set<Object> a, Object o) {
        return null;
    }

    @Override
    public Object length(Set<Object> a) {
        return null;
    }

    @Override
    public Object get(Set<Object> a, Object o) {
        return null;
    }

    @Override
    public Object set(Set<Object> a, Object o, Object v) {
        return null;
    }

    @Override
    public Object del(Set<Object> a, Object o) {
        return null;
    }

    @Override
    public Object iterator(Set<Object> a) {
        return null;
    }

    @Override
    public Object reversed(Set<Object> a) {
        return null;
    }

    @Override
    public Object unary_op(Set<Object> a, int unary_op) {
        return null;
    }

    @Override
    public Object binary_op(Set<Object> a, Object o, int binary_op) {
        return null;
    }

    @Override
    public Object rh_binary_op(Set<Object> a, Object o, int binary_op) {
        return null;
    }

    @Override
    public Object inplace_binary_op(Set<Object> a, Object o, int binary_op) {
        return null;
    }

    @Override
    public FMetaType metaType() {
        return null;
    }
}

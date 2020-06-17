package org.fugalang.core.object;

import java.util.Set;

public class FSet implements FType<Set<Object>> {

    static FSet INSTANCE = new FSet();

    private FSet() {

    }

    @Override
    public Object compare_op(Set<Object> a, Object o, int compare_op) {
        return null;
    }

    @Override
    public Object __getattr__(Set<Object> a, Object o) {
        return null;
    }

    @Override
    public Object __setattr__(Set<Object> a, Object o) {
        return null;
    }

    @Override
    public Object __delattr__(Set<Object> a, Object o) {
        return null;
    }

    @Override
    public Object __dir__(Set<Object> a, Object o) {
        return null;
    }

    @Override
    public Object __call__(Set<Object> a, Object o) {
        return null;
    }

    @Override
    public Object __len__(Set<Object> a) {
        return null;
    }

    @Override
    public Object __getitem__(Set<Object> a, Object o) {
        return null;
    }

    @Override
    public Object __setitem__(Set<Object> a, Object o) {
        return null;
    }

    @Override
    public Object __delitem__(Set<Object> a, Object o) {
        return null;
    }

    @Override
    public Object __iter__(Set<Object> a, Object o) {
        return null;
    }

    @Override
    public Object __reversed__(Set<Object> a, Object o) {
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
    public Object rh_binary_op(Set<Object> a, Object b, int binary_op) {
        return null;
    }

    @Override
    public Object inplace_binary_op(Set<Object> a, Object b, int binary_op) {
        return null;
    }

    @Override
    public Object __enter__(Set<Object> a, Object o) {
        return null;
    }

    @Override
    public Object __exit__(Set<Object> a, Object o) {
        return null;
    }
}

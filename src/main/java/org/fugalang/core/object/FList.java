package org.fugalang.core.object;

import java.util.List;

public class FList implements FType<List<Object>> {

    static FList INSTANCE = new FList();

    private FList() {
    }


    @Override
    public Object compare_op(List<Object> a, Object o, int compare_op) {
        return null;
    }

    @Override
    public Object __getattr__(List<Object> a, Object o) {
        return null;
    }

    @Override
    public Object __setattr__(List<Object> a, Object o) {
        return null;
    }

    @Override
    public Object __delattr__(List<Object> a, Object o) {
        return null;
    }

    @Override
    public Object __dir__(List<Object> a, Object o) {
        return null;
    }

    @Override
    public Object __call__(List<Object> a, Object o) {
        return null;
    }

    @Override
    public Object __len__(List<Object> a) {
        return null;
    }

    @Override
    public Object __getitem__(List<Object> a, Object o) {
        return null;
    }

    @Override
    public Object __setitem__(List<Object> a, Object o) {
        return null;
    }

    @Override
    public Object __delitem__(List<Object> a, Object o) {
        return null;
    }

    @Override
    public Object __iter__(List<Object> a, Object o) {
        return null;
    }

    @Override
    public Object __reversed__(List<Object> a, Object o) {
        return null;
    }

    @Override
    public Object unary_op(List<Object> a, int unary_op) {
        return null;
    }

    @Override
    public Object binary_op(List<Object> a, Object o, int binary_op) {
        return null;
    }

    @Override
    public Object rh_binary_op(List<Object> a, Object b, int binary_op) {
        return null;
    }

    @Override
    public Object inplace_binary_op(List<Object> a, Object b, int binary_op) {
        return null;
    }

    @Override
    public Object __enter__(List<Object> a, Object o) {
        return null;
    }

    @Override
    public Object __exit__(List<Object> a, Object o) {
        return null;
    }
}

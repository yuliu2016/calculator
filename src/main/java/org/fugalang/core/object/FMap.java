package org.fugalang.core.object;

import java.util.Map;

public class FMap implements FType<Map<Object, Object>> {

    static FMap INSTANCE = new FMap();

    private FMap() {

    }


    @Override
    public Object compare_op(Map<Object, Object> a, Object o, int compare_op) {
        return null;
    }

    @Override
    public Object __getattr__(Map<Object, Object> a, Object o) {
        return null;
    }

    @Override
    public Object __setattr__(Map<Object, Object> a, Object o, Object v) {
        return null;
    }

    @Override
    public Object __delattr__(Map<Object, Object> a, Object o) {
        return null;
    }

    @Override
    public Object __dir__(Map<Object, Object> a, Object o) {
        return null;
    }

    @Override
    public Object __len__(Map<Object, Object> a) {
        return null;
    }

    @Override
    public Object __getitem__(Map<Object, Object> a, Object o) {
        return null;
    }

    @Override
    public Object __setitem__(Map<Object, Object> a, Object o, Object v) {
        return null;
    }

    @Override
    public Object __delitem__(Map<Object, Object> a, Object o) {
        return null;
    }

    @Override
    public Object iterator(Map<Object, Object> a) {
        return null;
    }

    @Override
    public Object reversed(Map<Object, Object> a) {
        return null;
    }

    @Override
    public Object unary_op(Map<Object, Object> a, int unary_op) {
        return null;
    }

    @Override
    public Object binary_op(Map<Object, Object> a, Object o, int binary_op) {
        return null;
    }

    @Override
    public Object rh_binary_op(Map<Object, Object> a, Object b, int binary_op) {
        return null;
    }

    @Override
    public Object inplace_binary_op(Map<Object, Object> a, Object b, int binary_op) {
        return null;
    }

    @Override
    public Object context_enter(Map<Object, Object> a) {
        return null;
    }

    @Override
    public Object context_exit(Map<Object, Object> a, Object o) {
        return null;
    }
}
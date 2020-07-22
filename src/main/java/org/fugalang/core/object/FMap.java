package org.fugalang.core.object;

import org.fugalang.core.object.type.FMetaType;
import org.fugalang.core.object.type.FType;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public final class FMap implements FType<Map<Object, Object>> {

    static FMap INSTANCE = new FMap();

    public static Object fromMap(Map<?, ?> m) {
        return new LinkedHashMap<Object, Object>(m);
    }

    public static Object newMap() {
        return new LinkedHashMap<>();
    }

    public static Object emptyMap() {
        return Collections.emptyMap();
    }

    private FMap() {

    }


    @Override
    public Object compare_op(Map<Object, Object> a, Object o, int compare_op) {
        return null;
    }

    @Override
    public Object get(Map<Object, Object> a, int index) {
        return null;
    }

    @Override
    public Object set(Map<Object, Object> a, int index, Object v) {
        return null;
    }

    @Override
    public Object contains(Map<Object, Object> a, Object o) {
        return null;
    }

    @Override
    public Object length(Map<Object, Object> a) {
        return null;
    }

    @Override
    public Object get(Map<Object, Object> a, Object o) {
        return null;
    }

    @Override
    public Object set(Map<Object, Object> a, Object o, Object v) {
        return null;
    }

    @Override
    public Object del(Map<Object, Object> a, Object o) {
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
    public Object rh_binary_op(Map<Object, Object> a, Object o, int binary_op) {
        return null;
    }

    @Override
    public Object inplace_binary_op(Map<Object, Object> a, Object o, int binary_op) {
        return null;
    }

    @Override
    public FMetaType metaType() {
        return null;
    }
}
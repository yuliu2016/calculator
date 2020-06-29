package org.fugalang.core.object.type;

public class FDescriptor implements FType<FMetaType> {

    public static final FDescriptor INSTANCE = new FDescriptor();

    @Override
    public FMetaType metaType() {
        return null;
    }

    @Override
    public Object get(FMetaType a, int index) {
        return null;
    }

    @Override
    public Object set(FMetaType a, int index, Object v) {
        return null;
    }

    @Override
    public Object contains(FMetaType a, Object o) {
        return null;
    }

    @Override
    public Object length(FMetaType a) {
        return null;
    }

    @Override
    public Object get(FMetaType a, Object o) {
        return null;
    }

    @Override
    public Object set(FMetaType a, Object o, Object v) {
        return null;
    }

    @Override
    public Object del(FMetaType a, Object o) {
        return null;
    }

    @Override
    public Object iterator(FMetaType a) {
        return null;
    }

    @Override
    public Object reversed(FMetaType a) {
        return null;
    }

    @Override
    public Object compare_op(FMetaType a, Object o, int compare_op) {
        return null;
    }

    @Override
    public Object unary_op(FMetaType a, int unary_op) {
        return null;
    }

    @Override
    public Object binary_op(FMetaType a, Object o, int binary_op) {
        return null;
    }

    @Override
    public Object rh_binary_op(FMetaType a, Object o, int binary_op) {
        return null;
    }

    @Override
    public Object inplace_binary_op(FMetaType a, Object o, int binary_op) {
        return null;
    }

}

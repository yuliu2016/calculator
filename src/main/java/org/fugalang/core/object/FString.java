package org.fugalang.core.object;

public class FString implements FType<String> {

    static FString INSTANCE = new FString();

    private FString() {
    }

    @Override
    public Object compare_op(String a, Object o, int compare_op) {
        return null;
    }

    @Override
    public Object __getattr__(String s, Object o) {
        return null;
    }

    @Override
    public Object __setattr__(String s, Object o) {
        return null;
    }

    @Override
    public Object __delattr__(String s, Object o) {
        return null;
    }

    @Override
    public Object __dir__(String s, Object o) {
        return null;
    }

    @Override
    public Object __call__(String s, Object o) {
        return null;
    }

    @Override
    public Object __len__(String s) {
        return null;
    }

    @Override
    public Object __getitem__(String s, Object o) {
        return null;
    }

    @Override
    public Object __setitem__(String s, Object o) {
        return null;
    }

    @Override
    public Object __delitem__(String s, Object o) {
        return null;
    }

    @Override
    public Object __iter__(String s, Object o) {
        return null;
    }

    @Override
    public Object __reversed__(String s, Object o) {
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
    public Object __enter__(String s, Object o) {
        return null;
    }

    @Override
    public Object __exit__(String s, Object o) {
        return null;
    }
}

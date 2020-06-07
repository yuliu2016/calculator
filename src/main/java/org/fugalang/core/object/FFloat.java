package org.fugalang.core.object;

import java.math.BigInteger;

public final class FFloat implements FType<Double> {

    static FFloat INSTANCE = new FFloat();

    private FFloat() {
    }

    private static Double asDouble(Object o) {
        if (o.getClass() == Double.class) {
            return (double) o;
        }
        if (o.getClass() == BigInteger.class) {
            return ((BigInteger) o).doubleValue();
        }
        return null;
    }

    @Override
    public Object __repr__(Double a) {
        return Double.toString(a);
    }

    @Override
    public Object __str__(Double a) {
        return __repr__(a);
    }

    @Override
    public Object __format__(Double a) {
        return __repr__(a);
    }

    @Override
    public Object __hash__(Double a) {
        return a.hashCode();
    }

    @Override
    public Object __bool__(Double a) {
        return !Double.isNaN(a) && a != 0;
    }

    @Override
    public Object __lt__(Double a, Object b) {
        var c = asDouble(b);
        return c == null ? null : a < c;
    }

    @Override
    public Object __le__(Double a, Object b) {
        var c = asDouble(b);
        return c == null ? null : a <= c;
    }

    @Override
    public Object __eq__(Double a, Object b) {
        var c = asDouble(b);
        return c == null ? null : a == (double) c;
    }

    @Override
    public Object __ne__(Double a, Object b) {
        var c = asDouble(b);
        return c == null ? null : a != (double) c;
    }

    @Override
    public Object __gt__(Double a, Object b) {
        var c = asDouble(b);
        return c == null ? null : a > c;
    }

    @Override
    public Object __ge__(Double a, Object b) {
        var c = asDouble(b);
        return c == null ? null : a >= c;
    }

    @Override
    public Object __getattr__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __setattr__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __delattr__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __dir__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __call__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __len__(Double a) {
        return null;
    }

    @Override
    public Object __getitem__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __setitem__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __delitem__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __iter__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __reversed__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __contains__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __add__(Double a, Object o) {
        var b = asDouble(o);
        return b == null ? null : a + b;
    }

    @Override
    public Object __sub__(Double a, Object o) {
        var b = asDouble(o);
        return b == null ? null : a - b;
    }

    @Override
    public Object __mul__(Double a, Object o) {
        var b = asDouble(o);
        return b == null ? null : a * b;
    }

    @Override
    public Object __matmul__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __truediv__(Double a, Object o) {
        var b = asDouble(o);
        return b == null ? null : a / b;
    }

    @Override
    public Object __floordiv__(Double a, Object o) {
        var b = asDouble(o);
        return b == null ? null : (int) Math.floor(a / b);
    }

    @Override
    public Object __mod__(Double a, Object o) {
        var b = asDouble(o);
        return b == null ? null : a % b;
    }

    @Override
    public Object __divmod__(Double a, Object o) {
        var b = asDouble(o);
        return b == null ? null : new Double[]{a / b, a % b};
    }

    @Override
    public Object __pow__(Double a, Object o) {
        var b = asDouble(o);
        return b == null ? null : Math.pow(a, b);
    }

    @Override
    public Object __lshift__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __rshift__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __and__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __xor__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __or__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __radd__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __rsub__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __rmul__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __rmatmul__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __rtruediv__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __rfloordiv__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __rmod__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __rdivmod__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __rpow__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __rlshift__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __rrshift__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __rand__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __rxor__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __ror__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __iadd__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __isub__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __imul__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __imatmul__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __itruediv__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __ifloordiv__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __imod__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __idivmod__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __ipow__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __ilshift__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __irshift__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __iand__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __ixor__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __ior__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __neg__(Double a) {
        return -a;
    }

    @Override
    public Object __pos__(Double a) {
        return a;
    }

    @Override
    public Object __abs__(Double a) {
        return Math.abs(a);
    }

    @Override
    public Object __invert__(Double a) {
        return null;
    }

    @Override
    public Object __int__(Double a) {
        return a.intValue();
    }

    @Override
    public Object __float__(Double a) {
        return a;
    }

    @Override
    public Object __round__(Double a) {
        return Math.round(a);
    }

    @Override
    public Object __trunk__(Double a) {
        return null;
    }

    @Override
    public Object __floor__(Double a) {
        return Math.floor(a);
    }

    @Override
    public Object __ceil__(Double a) {
        return Math.ceil(a);
    }

    @Override
    public Object __enter__(Double a, Object o) {
        return null;
    }

    @Override
    public Object __exit__(Double a, Object o) {
        return null;
    }
}

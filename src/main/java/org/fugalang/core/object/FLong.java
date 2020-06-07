package org.fugalang.core.object;

import java.math.BigInteger;


public final class FLong implements FType<BigInteger> {

    static FLong INSTANCE = new FLong();

    private FLong() {
    }

    @Override
    public Object __repr__(BigInteger a) {
        return a.toString();
    }

    @Override
    public Object __str__(BigInteger a) {
        return __repr__(a);
    }

    @Override
    public Object __format__(BigInteger a) {
        return __repr__(a);
    }

    @Override
    public Object __hash__(BigInteger a) {
        return a.hashCode();
    }

    @Override
    public Object __bool__(BigInteger a) {
        return a.signum() != 0;
    }

    @Override
    public Object __lt__(BigInteger a, Object b) {
        if (b.getClass() == BigInteger.class) return a.compareTo((BigInteger) b) < 0;
        if (b.getClass() == Double.class) return a.doubleValue() < (Double) b;
        return null;
    }

    @Override
    public Object __le__(BigInteger a, Object b) {
        if (b.getClass() == BigInteger.class) return a.compareTo((BigInteger) b) < 0;
        if (b.getClass() == Double.class) return a.doubleValue() <= (Double) b;
        return null;
    }

    @Override
    public Object __eq__(BigInteger a, Object b) {
        if (b.getClass() == BigInteger.class) return a.compareTo((BigInteger) b) < 0;
        if (b.getClass() == Double.class) return a.doubleValue() == (Double) b;
        return null;
    }

    @Override
    public Object __ne__(BigInteger a, Object b) {
        if (b.getClass() == BigInteger.class) return a.compareTo((BigInteger) b) < 0;
        if (b.getClass() == Double.class) return a.doubleValue() != (Double) b;
        return null;
    }

    @Override
    public Object __gt__(BigInteger a, Object b) {
        if (b.getClass() == BigInteger.class) return a.compareTo((BigInteger) b) < 0;
        if (b.getClass() == Double.class) return a.doubleValue() > (Double) b;
        return null;
    }

    @Override
    public Object __ge__(BigInteger a, Object b) {
        if (b.getClass() == BigInteger.class) return a.compareTo((BigInteger) b) < 0;
        if (b.getClass() == Double.class) return a.doubleValue() >= (Double) b;
        return null;
    }

    @Override
    public Object __getattr__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __setattr__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __delattr__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __dir__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __call__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __len__(BigInteger a) {
        return null;
    }

    @Override
    public Object __getitem__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __setitem__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __delitem__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __iter__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __reversed__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __contains__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __add__(BigInteger a, Object o) {
        if (o instanceof BigInteger) return a.add((BigInteger) o);
        if (o instanceof Double) return FConst.FloatType.__add__(a.doubleValue(), o);
        return null;
    }

    @Override
    public Object __sub__(BigInteger a, Object o) {
        if (o instanceof BigInteger) return a.subtract((BigInteger) o);
        if (o instanceof Double) return FConst.FloatType.__sub__(a.doubleValue(), o);
        return null;
    }

    @Override
    public Object __mul__(BigInteger a, Object o) {
        if (o instanceof BigInteger) return a.multiply((BigInteger) o);
        if (o instanceof Double) return FConst.FloatType.__mul__(a.doubleValue(), o);
        return null;
    }

    @Override
    public Object __matmul__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __truediv__(BigInteger a, Object o) {
        return FConst.FloatType.__truediv__(a.doubleValue(), o);
    }

    @Override
    public Object __floordiv__(BigInteger a, Object o) {
        return FConst.FloatType.__floordiv__(a.doubleValue(), o);
    }

    @Override
    public Object __mod__(BigInteger a, Object o) {
        if (o instanceof BigInteger) return a.mod((BigInteger) o);
        if (o instanceof Double) return FConst.FloatType.__mod__(a.doubleValue(), o);
        return null;
    }

    @Override
    public Object __divmod__(BigInteger a, Object o) {
        if (o instanceof BigInteger) return a.divideAndRemainder((BigInteger) o);
        if (o instanceof Double) return FConst.FloatType.__mod__(a.doubleValue(), o);
        return null;
    }

    @Override
    public Object __pow__(BigInteger a, Object o) {
        if (o instanceof BigInteger) return a.pow(((BigInteger) o).intValueExact());
        if (o instanceof Double) return FConst.FloatType.__pow__((Double) o, a);
        return null;
    }

    @Override
    public Object __lshift__(BigInteger a, Object o) {
        if (o instanceof BigInteger) return a.shiftLeft(((BigInteger) o).intValueExact());
        return null;
    }

    @Override
    public Object __rshift__(BigInteger a, Object o) {
        if (o instanceof BigInteger) return a.shiftRight(((BigInteger) o).intValueExact());
        return null;
    }

    @Override
    public Object __and__(BigInteger a, Object o) {
        if (o instanceof BigInteger) return a.and((BigInteger) o);
        return null;
    }

    @Override
    public Object __xor__(BigInteger a, Object o) {
        if (o instanceof BigInteger) return a.xor((BigInteger) o);
        return null;
    }

    @Override
    public Object __or__(BigInteger a, Object o) {
        if (o instanceof BigInteger) return a.or((BigInteger) o);
        return null;
    }

    @Override
    public Object __radd__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __rsub__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __rmul__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __rmatmul__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __rtruediv__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __rfloordiv__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __rmod__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __rdivmod__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __rpow__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __rlshift__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __rrshift__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __rand__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __rxor__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __ror__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __iadd__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __isub__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __imul__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __imatmul__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __itruediv__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __ifloordiv__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __imod__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __idivmod__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __ipow__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __ilshift__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __irshift__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __iand__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __ixor__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __ior__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __neg__(BigInteger a) {
        return a.negate();
    }

    @Override
    public Object __pos__(BigInteger a) {
        return a;
    }

    @Override
    public Object __abs__(BigInteger a) {
        return a.abs();
    }

    @Override
    public Object __invert__(BigInteger a) {
        return a.not();
    }

    @Override
    public Object __int__(BigInteger a) {
        return a;
    }

    @Override
    public Object __float__(BigInteger a) {
        return a.doubleValue();
    }

    @Override
    public Object __round__(BigInteger a) {
        return a.doubleValue();
    }

    @Override
    public Object __trunk__(BigInteger a) {
        return a;
    }

    @Override
    public Object __floor__(BigInteger a) {
        return a;
    }

    @Override
    public Object __ceil__(BigInteger a) {
        return a;
    }

    @Override
    public Object __enter__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __exit__(BigInteger a, Object o) {
        return null;
    }
}

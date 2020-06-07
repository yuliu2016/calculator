package org.fugalang.core.object;

import org.fugalang.core.eval.FEval;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.StringJoiner;

public final class FTuple implements FType<Object[]> {

    static FTuple INSTANCE = new FTuple();

    private FTuple() {
    }


    @Override
    public Object __repr__(Object[] a) {
        if (a.length == 0) {
            return "()";
        }
        var sj = new StringJoiner(", ", "(", ")");
        for (Object o : a) {
            sj.add(o.toString());
        }
        return sj.toString();
    }

    @Override
    public Object __str__(Object[] a) {
        return __repr__(a);
    }

    @Override
    public Object __format__(Object[] a) {
        return __repr__(a);
    }

    @Override
    public Object __hash__(Object[] a) {
        return Arrays.hashCode(a);
    }

    @Override
    public Object __bool__(Object[] a) {
        return a.length != 0;
    }

    @Override
    public Object __lt__(Object[] a, Object b) {
        if (!(b instanceof Object[])) return null;
        var c = (Object[]) b;
        int m = Math.max(a.length, c.length);
        for (int i = 0; i < m; i++) {
            var x = a[i];
            var y = c[i];
            if (!FEval.compareEqIsTrue(x, y)) {
                return FEval.compareLt(x, y);
            }
        }
        return a.length < c.length;
    }

    @Override
    public Object __le__(Object[] a, Object b) {
        if (!(b instanceof Object[])) return null;
        var c = (Object[]) b;
        int m = Math.max(a.length, c.length);
        for (int i = 0; i < m; i++) {
            var x = a[i];
            var y = c[i];
            if (!FEval.compareEqIsTrue(x, y)) {
                return FEval.compareLe(x, y);
            }
        }
        return a.length <= c.length;
    }

    private static boolean tupleEquals(Object[] a, Object[] c) {
        if (a.length != c.length) {
            return false;
        }
        int i = 0;
        while (i < a.length) {
            var x = a[i];
            var y = c[i];
            if (FEval.compareEqIsTrue(x, y)) {
                i++;
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object __eq__(Object[] a, Object b) {
        if (!(b instanceof Object[])) return null;
        return tupleEquals(a, (Object[]) b);
    }

    @Override
    public Object __ne__(Object[] a, Object b) {
        if (!(b instanceof Object[])) return null;
        return !tupleEquals(a, (Object[]) b);
    }

    @Override
    public Object __gt__(Object[] a, Object b) {
        if (!(b instanceof Object[])) return null;
        var c = (Object[]) b;
        int m = Math.max(a.length, c.length);
        for (int i = 0; i < m; i++) {
            var x = a[i];
            var y = c[i];
            if (!FEval.compareEqIsTrue(x, y)) {
                return FEval.compareGt(x, y);
            }
        }
        return a.length > c.length;
    }

    @Override
    public Object __ge__(Object[] a, Object b) {
        if (!(b instanceof Object[])) return null;
        var c = (Object[]) b;
        int m = Math.max(a.length, c.length);
        for (int i = 0; i < m; i++) {
            var x = a[i];
            var y = c[i];
            if (!FEval.compareEqIsTrue(x, y)) {
                return FEval.compareGe(x, y);
            }
        }
        return a.length >= c.length;
    }

    @Override
    public Object __getattr__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __setattr__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __delattr__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __dir__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __call__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __len__(Object[] a) {
        return a.length;
    }

    @Override
    public Object __getitem__(Object[] a, Object o) {
        if (o.getClass() == BigInteger.class) {
            var index = ((BigInteger) o).intValue();
            if (index < 0 || index >= a.length) {
                throw new IndexOutOfBoundsException();
            }
            return a[index];
        }
        return null;
    }

    @Override
    public Object __setitem__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __delitem__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __iter__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __reversed__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __contains__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __add__(Object[] a, Object o) {
        if (o instanceof Object[]) {
            var b = ((Object[]) o);
            var c = new Object[a.length + b.length];
            System.arraycopy(a, 0, c, 0, a.length);
            System.arraycopy(b, 0, c, a.length, b.length);
            return c;
        }
        return null;
    }

    @Override
    public Object __sub__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __mul__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __matmul__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __truediv__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __floordiv__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __mod__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __divmod__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __pow__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __lshift__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __rshift__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __and__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __xor__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __or__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __radd__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __rsub__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __rmul__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __rmatmul__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __rtruediv__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __rfloordiv__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __rmod__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __rdivmod__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __rpow__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __rlshift__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __rrshift__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __rand__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __rxor__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __ror__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __iadd__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __isub__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __imul__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __imatmul__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __itruediv__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __ifloordiv__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __imod__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __idivmod__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __ipow__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __ilshift__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __irshift__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __iand__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __ixor__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __ior__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __neg__(Object[] a) {
        return null;
    }

    @Override
    public Object __pos__(Object[] a) {
        return null;
    }

    @Override
    public Object __abs__(Object[] a) {
        return null;
    }

    @Override
    public Object __invert__(Object[] a) {
        return null;
    }

    @Override
    public Object __int__(Object[] a) {
        return null;
    }

    @Override
    public Object __float__(Object[] a) {
        return null;
    }

    @Override
    public Object __round__(Object[] a) {
        return null;
    }

    @Override
    public Object __trunk__(Object[] a) {
        return null;
    }

    @Override
    public Object __floor__(Object[] a) {
        return null;
    }

    @Override
    public Object __ceil__(Object[] a) {
        return null;
    }

    @Override
    public Object __enter__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __exit__(Object[] a, Object o) {
        return null;
    }
}

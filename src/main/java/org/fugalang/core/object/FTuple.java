package org.fugalang.core.object;

import org.fugalang.core.eval.FEval;
import org.fugalang.core.opcode.BinaryOp;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.StringJoiner;

import static org.fugalang.core.opcode.CompareOp.*;

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

    private static Object arrayCompare(Object[] a, Object b, int cmp_op) {
        if (!(b instanceof Object[])) return null;
        var c = (Object[]) b;
        int m = Math.max(a.length, c.length);
        for (int i = 0; i < m; i++) {
            var x = a[i];
            var y = c[i];
            if (!FEval.isEqual(x, y)) {
                return FEval.isTrue(FEval.compare(x, y, cmp_op));
            }
        }
        return FEval.compareOp(cmp_op, Integer.compare(a.length, c.length));
    }

    private static boolean arrayEquals(Object[] a, Object[] c) {
        if (a.length != c.length) {
            return false;
        }
        int i = 0;
        while (i < a.length) {
            var x = a[i];
            var y = c[i];
            if (FEval.isEqual(x, y)) {
                i++;
            } else {
                return false;
            }
        }
        return true;
    }

    private static boolean arrayContains(Object[] a, Object b) {
        for (Object o : a) {
            if (FEval.isEqual(o, b)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object compare_op(Object[] a, Object o, int compare_op) {
        switch (compare_op) {
            case CMP_LT:
            case CMP_LE:
            case CMP_GT:
            case CMP_GE:
                return arrayCompare(a, o, compare_op);
            case CMP_EQ:
                if (!(o instanceof Object[])) return null;
                return arrayEquals(a, (Object[]) o);
            case CMP_NE:
                if (!(o instanceof Object[])) return null;
                return !arrayEquals(a, (Object[]) o);
            case CMP_IN:
                return arrayContains(a, o);
            case CMP_NOT_IN:
                return !arrayContains(a, o);
            default:
                return null;
        }
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
    public Object binary_op(Object[] a, Object o, int binary_op) {
        if (binary_op == BinaryOp.BINOP_ADD) {
            if (o instanceof Object[]) {
                var b = ((Object[]) o);
                var c = new Object[a.length + b.length];
                System.arraycopy(a, 0, c, 0, a.length);
                System.arraycopy(b, 0, c, a.length, b.length);
                return c;
            }
        }
        return null;
    }

    @Override
    public Object rh_binary_op(Object[] a, Object b, int binary_op) {
        return null;
    }

    @Override
    public Object inplace_binary_op(Object[] a, Object b, int binary_op) {
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

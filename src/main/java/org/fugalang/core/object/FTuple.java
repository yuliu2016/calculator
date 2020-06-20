package org.fugalang.core.object;

import org.fugalang.core.eval.FEval;
import org.fugalang.core.opcode.BinaryOp;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.StringJoiner;

import static org.fugalang.core.opcode.CompareOp.*;
import static org.fugalang.core.opcode.UnaryOp.*;

public final class FTuple implements FType<Object[]> {

    static FTuple INSTANCE = new FTuple();

    private FTuple() {
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

    @SuppressWarnings("unused")
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
//            case CMP_IN:
//                return arrayContains(a, o);
//            case CMP_NOT_IN:
//                return !arrayContains(a, o);
            default:
                return null;
        }
    }

    @Override
    public Object __getattr__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object __setattr__(Object[] a, Object o, Object v) {
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
    public Object __setitem__(Object[] a, Object o, Object v) {
        return null;
    }

    @Override
    public Object __delitem__(Object[] a, Object o) {
        return null;
    }

    @Override
    public Object iterator(Object[] a) {
        return null;
    }

    @Override
    public Object reversed(Object[] a) {
        return null;
    }

    @Override
    public Object unary_op(Object[] a, int unary_op) {
        switch (unary_op) {
            case UNARY_BOOL:
                return a.length != 0;
            case UNARY_HASH:
                return Arrays.hashCode(a);
            case UNARY_STR:
            case UNARY_REPR:
                if (a.length == 0) {
                    return "()";
                }
                var sj = new StringJoiner(", ", "(", ")");
                for (Object o : a) {
                    sj.add(o.toString());
                }
                return sj.toString();
            case UNARY_INVERT:
            case UNARY_TRUNK:
            case UNARY_NEG:
            case UNARY_POS:
            case UNARY_FLOAT:
            case UNARY_ABS:
            case UNARY_INT:
            case UNARY_ROUND:
            case UNARY_FLOOR:
            case UNARY_CEIL:
            default:
                return null;
        }
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
    public Object context_enter(Object[] a) {
        return null;
    }

    @Override
    public Object context_exit(Object[] a, Object o) {
        return null;
    }
}

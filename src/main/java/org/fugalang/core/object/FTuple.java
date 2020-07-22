package org.fugalang.core.object;

import org.fugalang.core.eval.FAbstract;
import org.fugalang.core.object.type.FMetaType;
import org.fugalang.core.object.type.FType;
import org.fugalang.core.opcode.BinaryOp;

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
            if (!FAbstract.equals(x, y)) {
                return FAbstract.isTrue(FAbstract.compare(x, y, cmp_op));
            }
        }
        return FAbstract.compareOp(cmp_op, Integer.compare(a.length, c.length));
    }

    private static boolean arrayEquals(Object[] a, Object[] c) {
        if (a.length != c.length) {
            return false;
        }
        int i = 0;
        while (i < a.length) {
            var x = a[i];
            var y = c[i];
            if (FAbstract.equals(x, y)) {
                i++;
            } else {
                return false;
            }
        }
        return true;
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
            default:
                return null;
        }
    }

    @Override
    public Object get(Object[] a, int index) {
        return null;
    }

    @Override
    public Object set(Object[] a, int index, Object v) {
        return null;
    }

    @Override
    public Object contains(Object[] a, Object o) {
        for (Object o1 : a) {
            if (FAbstract.equals(o1, o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object length(Object[] a) {
        return a.length;
    }

    @Override
    public Object get(Object[] a, Object o) {
        if (o.getClass() == Long.class) {
            var index = (int) (long) o;
            if (index < 0 || index >= a.length) {
                throw new IndexOutOfBoundsException();
            }
            return a[index];
        }
        return null;
    }

    @Override
    public Object set(Object[] a, Object o, Object v) {
        return null;
    }

    @Override
    public Object del(Object[] a, Object o) {
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
    public Object rh_binary_op(Object[] a, Object o, int binary_op) {
        return null;
    }

    @Override
    public Object inplace_binary_op(Object[] a, Object o, int binary_op) {
        return null;
    }

    @Override
    public FMetaType metaType() {
        return null;
    }
}

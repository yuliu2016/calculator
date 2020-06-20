package org.fugalang.core.object;

import org.fugalang.core.eval.FEval;

import java.math.BigInteger;
import java.util.List;

import static org.fugalang.core.opcode.CompareOp.*;

public class FList implements FType<List<Object>> {

    static FList INSTANCE = new FList();

    private FList() {
    }

    private static Object listCompare(List<Object> a, Object b, int cmp_op) {
        if (!(b instanceof List)) return null;
        var c = (List<?>) b;
        int m = Math.max(a.size(), c.size());
        for (int i = 0; i < m; i++) {
            var x = a.get(i);
            var y = c.get(i);
            if (!FEval.isEqual(x, y)) {
                return FEval.isTrue(FEval.compare(x, y, cmp_op));
            }
        }
        return FEval.compareOp(cmp_op, Integer.compare(a.size(), c.size()));
    }

    private static boolean listEquals(List<Object> a, List<?> c) {
        if (a.size() != c.size()) {
            return false;
        }
        int i = 0;
        while (i < a.size()) {
            var x = a.get(i);
            var y = c.get(i);
            if (FEval.isEqual(x, y)) {
                i++;
            } else {
                return false;
            }
        }
        return true;
    }

    @SuppressWarnings("unused")
    private static boolean listContains(List<Object> a, Object b) {
        for (Object o : a) {
            if (FEval.isEqual(o, b)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public Object compare_op(List<Object> a, Object o, int compare_op) {
        switch (compare_op) {
            case CMP_LT:
            case CMP_LE:
            case CMP_GT:
            case CMP_GE:
                return listCompare(a, o, compare_op);
            case CMP_EQ:
                if (!(o instanceof List)) return null;
                return listEquals(a, (List<?>) o);
            case CMP_NE:
                if (!(o instanceof List)) return null;
                return !listEquals(a, (List<?>) o);
//            case CMP_IN:
//                return listContains(a, o);
//            case CMP_NOT_IN:
//                return !listContains(a, o);
            default:
                return null;
        }
    }

    @Override
    public Object __getattr__(List<Object> a, Object o) {
        return null;
    }

    @Override
    public Object __setattr__(List<Object> a, Object o, Object v) {
        return null;
    }

    @Override
    public Object __delattr__(List<Object> a, Object o) {
        return null;
    }

    @Override
    public Object __dir__(List<Object> a, Object o) {
        return null;
    }

    @Override
    public Object __len__(List<Object> a) {
        return null;
    }

    @Override
    public Object __getitem__(List<Object> a, Object o) {
        if (o.getClass() == BigInteger.class) {
            var index = ((BigInteger) o).intValue();
            if (index < 0 || index >= a.size()) {
                throw new IndexOutOfBoundsException();
            }
            return a.get(index);
        }
        return null;
    }

    @Override
    public Object __setitem__(List<Object> a, Object o, Object v) {
        return null;
    }

    @Override
    public Object __delitem__(List<Object> a, Object o) {
        return null;
    }

    @Override
    public Object iterator(List<Object> a) {
        return null;
    }

    @Override
    public Object reversed(List<Object> a) {
        return null;
    }

    @Override
    public Object unary_op(List<Object> a, int unary_op) {
        return null;
    }

    @Override
    public Object binary_op(List<Object> a, Object o, int binary_op) {
        return null;
    }

    @Override
    public Object rh_binary_op(List<Object> a, Object b, int binary_op) {
        return null;
    }

    @Override
    public Object inplace_binary_op(List<Object> a, Object b, int binary_op) {
        return null;
    }

    @Override
    public Object context_enter(List<Object> a) {
        return null;
    }

    @Override
    public Object context_exit(List<Object> a, Object o) {
        return null;
    }
}

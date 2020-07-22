package org.fugalang.core.object;

import org.fugalang.core.eval.FAbstract;
import org.fugalang.core.object.type.FMetaType;
import org.fugalang.core.object.type.FType;

import static org.fugalang.core.opcode.BinaryOp.*;
import static org.fugalang.core.opcode.UnaryOp.*;

public final class FFloat implements FType<Double> {

    static FFloat INSTANCE = new FFloat();

    private FFloat() {
    }

    private static Double asDouble(Object o) {
        if (o.getClass() == Double.class) {
            return (double) o;
        }
        if (o.getClass() == Long.class) {
            return ((Long) o).doubleValue();
        }
        return null;
    }

    @Override
    public Object compare_op(Double a, Object o, int compare_op) {
        double y;
        if (o.getClass() == Double.class) y = (double) o;
        else if (o.getClass() == Long.class) y = ((Long) o).doubleValue();
        else return null;
        return FAbstract.compareOp(compare_op, Double.compare(a, y));
    }

    @Override
    public Object get(Double a, int index) {
        return null;
    }

    @Override
    public Object set(Double a, int index, Object v) {
        return null;
    }

    @Override
    public Object contains(Double a, Object o) {
        return null;
    }

    @Override
    public Object length(Double a) {
        return null;
    }

    @Override
    public Object get(Double a, Object o) {
        return null;
    }

    @Override
    public Object set(Double a, Object o, Object v) {
        return null;
    }

    @Override
    public Object del(Double a, Object o) {
        return null;
    }

    @Override
    public Object iterator(Double a) {
        return null;
    }

    @Override
    public Object reversed(Double a) {
        return null;
    }

    @Override
    public Object unary_op(Double a, int unary_op) {
        switch (unary_op) {
            case UNARY_NEG:
                return -a;
            case UNARY_POS:
            case UNARY_FLOAT:
                return a;
            case UNARY_ABS:
                return Math.abs(a);
            case UNARY_INT:
            case UNARY_INVERT:
            case UNARY_TRUNK:
                return a.intValue();
            case UNARY_ROUND:
                return Math.round(a);
            case UNARY_FLOOR:
                return Math.floor(a);
            case UNARY_CEIL:
                return Math.ceil(a);
            case UNARY_BOOL:
                return !Double.isNaN(a) && a != 0;
            case UNARY_HASH:
                return a.hashCode();
            case UNARY_STR:
            case UNARY_REPR:
                return a.toString();
            default:
                return null;
        }
    }

    @Override
    public Object binary_op(Double a, Object o, int binary_op) {
        var b = asDouble(o);
        if (b == null) return null;
        switch (binary_op) {
            case BINOP_ADD:
                return a + b;
            case BINOP_SUBTRACT:
                return a - b;
            case BINOP_MULTIPLY:
                return a * b;
            case BINOP_DIVIDE:
                return a / b;
            case BINOP_FLOORDIV:
                return (int) Math.floor(a / b);
            case BINOP_MOD:
                return a % b;
            case BINOP_DIVMOD:
                return new Object[]{a / b, a % b};
            case BINOP_POW:
                return Math.pow(a, b);
            case BINOP_LSHIFT:
            case BINOP_RSHIFT:
            case BINOP_AND:
            case BINOP_OR:
            case BINOP_XOR:
            case BINOP_MATMUL:
            default:
                return null;
        }
    }

    @Override
    public Object rh_binary_op(Double a, Object o, int binary_op) {
        return binary_op(a, o, binary_op);
    }

    @Override
    public Object inplace_binary_op(Double a, Object o, int binary_op) {
        return binary_op(a, o, binary_op);
    }

    @Override
    public FMetaType metaType() {
        return null;
    }
}

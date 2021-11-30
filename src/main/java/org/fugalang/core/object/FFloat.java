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
        return switch (unary_op) {
            case UNARY_NEG -> -a;
            case UNARY_POS, UNARY_FLOAT -> a;
            case UNARY_ABS -> Math.abs(a);
            case UNARY_INT, UNARY_INVERT, UNARY_TRUNK -> a.intValue();
            case UNARY_ROUND -> Math.round(a);
            case UNARY_FLOOR -> Math.floor(a);
            case UNARY_CEIL -> Math.ceil(a);
            case UNARY_BOOL -> !Double.isNaN(a) && a != 0;
            case UNARY_HASH -> a.hashCode();
            case UNARY_STR, UNARY_REPR -> a.toString();
            default -> null;
        };
    }

    @Override
    public Object binary_op(Double a, Object o, int binary_op) {
        var b = asDouble(o);
        if (b == null) return null;
        return switch (binary_op) {
            case BINOP_ADD -> a + b;
            case BINOP_SUBTRACT -> a - b;
            case BINOP_MULTIPLY -> a * b;
            case BINOP_DIVIDE -> a / b;
            case BINOP_FLOORDIV -> (int) Math.floor(a / b);
            case BINOP_MOD -> a % b;
            case BINOP_DIVMOD -> new Object[]{a / b, a % b};
            case BINOP_POW -> Math.pow(a, b);
            case BINOP_LSHIFT, BINOP_RSHIFT, BINOP_AND, BINOP_OR, BINOP_XOR, BINOP_MATMUL -> null;
            default -> null;
        };
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

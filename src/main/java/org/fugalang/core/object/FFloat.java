package org.fugalang.core.object;

import org.fugalang.core.eval.FEval;

import java.math.BigInteger;

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
        if (o.getClass() == BigInteger.class) {
            return ((BigInteger) o).doubleValue();
        }
        return null;
    }

    @Override
    public Object compare_op(Double a, Object o, int compare_op) {
        double y;
        if (o.getClass() == Double.class) y = (double) o;
        else if (o.getClass() == BigInteger.class) y = ((BigInteger) o).doubleValue();
        else return null;
        return FEval.compareOp(compare_op, Double.compare(a, y));
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
    public Object rh_binary_op(Double a, Object b, int binary_op) {
        return binary_op(a, b, binary_op);
    }

    @Override
    public Object inplace_binary_op(Double a, Object b, int binary_op) {
        return binary_op(a, b, binary_op);
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

package org.fugalang.core.object;

import org.fugalang.core.eval.FEval;

import java.math.BigInteger;

import static org.fugalang.core.opcode.BinaryOp.*;
import static org.fugalang.core.opcode.UnaryOp.*;


public final class FLong implements FType<BigInteger> {

    static FLong INSTANCE = new FLong();

    private FLong() {
    }

    @Override
    public Object compare_op(BigInteger a, Object o, int compare_op) {
        int cmp_result;
        if (o.getClass() == BigInteger.class) {
            cmp_result = a.compareTo((BigInteger) o);
        } else if (o.getClass() == Double.class) {
            cmp_result = Double.compare(a.doubleValue(), (Double) o);
        } else {
            return null;
        }
        return FEval.compareOp(compare_op, cmp_result);
    }

    @Override
    public Object __getattr__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __setattr__(BigInteger a, Object o, Object v) {
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
    public Object __len__(BigInteger a) {
        return null;
    }

    @Override
    public Object __getitem__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object __setitem__(BigInteger a, Object o, Object v) {
        return null;
    }

    @Override
    public Object __delitem__(BigInteger a, Object o) {
        return null;
    }

    @Override
    public Object iterator(BigInteger a) {
        return null;
    }

    @Override
    public Object reversed(BigInteger a) {
        return null;
    }

    @Override
    public Object unary_op(BigInteger a, int unary_op) {
        switch (unary_op) {
            case UNARY_NEG:
                return a.negate();
            case UNARY_POS:
            case UNARY_INT:
            case UNARY_FLOOR:
            case UNARY_CEIL:
                return a;
            case UNARY_ROUND:
            case UNARY_FLOAT:
                return a.doubleValue();
            case UNARY_ABS:
                return a.abs();
            case UNARY_BOOL:
                return a.signum() != 0;
            case UNARY_HASH:
                return a.hashCode();
            case UNARY_STR:
            case UNARY_REPR:
                return a.toString();
            case UNARY_INVERT:
                return a.not();
            case UNARY_TRUNK:
            default:
                return null;
        }
    }

    @Override
    public Object binary_op(BigInteger a, Object o, int binary_op) {
        if (o.getClass() != BigInteger.class) return null;
        var b = ((BigInteger) o);
        switch (binary_op) {
            case BINOP_ADD:
                return a.add(b);
            case BINOP_SUBTRACT:
                return a.subtract(b);
            case BINOP_MULTIPLY:
                return a.multiply(b);
            case BINOP_DIVIDE:
                return a.doubleValue() / b.doubleValue();
            case BINOP_FLOORDIV:
                return a.divide(b);
            case BINOP_MOD:
                return a.mod(b);
            case BINOP_DIVMOD:
                return a.divideAndRemainder(b);
            case BINOP_POW:
                return a.pow(b.intValue());
            case BINOP_LSHIFT:
                return a.shiftLeft(b.intValue());
            case BINOP_RSHIFT:
                return a.shiftRight(b.intValue());
            case BINOP_AND:
                return a.and(b);
            case BINOP_OR:
                return a.or(b);
            case BINOP_XOR:
                return a.xor(b);
            case BINOP_MATMUL:
            default:
                return null;
        }
    }

    @Override
    public Object rh_binary_op(BigInteger a, Object b, int binary_op) {
        return null;
    }

    @Override
    public Object inplace_binary_op(BigInteger a, Object b, int binary_op) {
        return null;
    }

    @Override
    public Object context_enter(BigInteger a) {
        return null;
    }

    @Override
    public Object context_exit(BigInteger a, Object o) {
        return null;
    }
}

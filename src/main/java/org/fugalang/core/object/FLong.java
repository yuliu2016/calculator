package org.fugalang.core.object;

import org.fugalang.core.eval.FEval;

import java.math.BigInteger;

import static org.fugalang.core.opcode.BinaryOp.*;


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

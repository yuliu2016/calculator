package org.fugalang.core.object;

import org.fugalang.core.eval.FAbstract;
import org.fugalang.core.object.type.FMetaType;
import org.fugalang.core.object.type.FType;

import static org.fugalang.core.opcode.BinaryOp.*;
import static org.fugalang.core.opcode.UnaryOp.*;


public final class FLong implements FType<Long> {

    static FLong INSTANCE = new FLong();

    private FLong() {
    }

    @Override
    public Object compare_op(Long a, Object o, int compare_op) {
        int cmp_result;
        if (o.getClass() == Long.class) {
            cmp_result = Long.compare(a, (Long) o);
        } else if (o.getClass() == Double.class) {
            cmp_result = Double.compare(a.doubleValue(), (Double) o);
        } else {
            return null;
        }
        return FAbstract.compareOp(compare_op, cmp_result);
    }

    @Override
    public Object get(Long a, int index) {
        return null;
    }

    @Override
    public Object set(Long a, int index, Object v) {
        return null;
    }

    @Override
    public Object contains(Long a, Object o) {
        return null;
    }

    @Override
    public Object length(Long a) {
        return null;
    }

    @Override
    public Object get(Long a, Object o) {
        return null;
    }

    @Override
    public Object set(Long a, Object o, Object v) {
        return null;
    }

    @Override
    public Object del(Long a, Object o) {
        return null;
    }

    @Override
    public Object iterator(Long a) {
        return null;
    }

    @Override
    public Object reversed(Long a) {
        return null;
    }

    @Override
    public Object unary_op(Long a, int unary_op) {
        switch (unary_op) {
            case UNARY_NEG:
                return -a;
            case UNARY_POS:
            case UNARY_INT:
            case UNARY_FLOOR:
            case UNARY_CEIL:
                return a;
            case UNARY_ROUND:
            case UNARY_FLOAT:
                return a.doubleValue();
            case UNARY_ABS:
                return Math.abs(a);
            case UNARY_BOOL:
                return a != 0;
            case UNARY_HASH:
                return a.hashCode();
            case UNARY_STR:
            case UNARY_REPR:
                return a.toString();
            case UNARY_INVERT:
                return ~a;
            case UNARY_TRUNK:
            default:
                return null;
        }
    }

    @Override
    public Object binary_op(Long a, Object o, int binary_op) {
        if (o.getClass() != Long.class) return null;
        var b = ((Long) o);
        switch (binary_op) {
            case BINOP_ADD:
                return a + b;
            case BINOP_SUBTRACT:
                return a - b;
            case BINOP_MULTIPLY:
                return a * b;
            case BINOP_DIVIDE:
                return a.doubleValue() / b.doubleValue();
            case BINOP_FLOORDIV:
                return a / b;
            case BINOP_MOD:
                return a % b;
            case BINOP_DIVMOD:
                return new Object[]{a / b, a % b};
            case BINOP_POW:
                return Math.pow(a, b);
            case BINOP_LSHIFT:
                return a << b;
            case BINOP_RSHIFT:
                return a >> b;
            case BINOP_AND:
                return a & b;
            case BINOP_OR:
                return a | b;
            case BINOP_XOR:
                return a ^ b;
            case BINOP_MATMUL:
            default:
                return null;
        }
    }

    @Override
    public Object rh_binary_op(Long a, Object o, int binary_op) {
        return null;
    }

    @Override
    public Object inplace_binary_op(Long a, Object o, int binary_op) {
        return null;
    }

    @Override
    public FMetaType metaType() {
        return null;
    }
}

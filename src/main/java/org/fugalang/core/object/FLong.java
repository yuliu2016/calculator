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
        return switch (unary_op) {
            case UNARY_NEG -> -a;
            case UNARY_POS, UNARY_INT, UNARY_FLOOR, UNARY_CEIL -> a;
            case UNARY_ROUND, UNARY_FLOAT -> a.doubleValue();
            case UNARY_ABS -> Math.abs(a);
            case UNARY_BOOL -> a != 0;
            case UNARY_HASH -> a.hashCode();
            case UNARY_STR, UNARY_REPR -> a.toString();
            case UNARY_INVERT -> ~a;
            case UNARY_TRUNK -> null;
            default -> null;
        };
    }

    @Override
    public Object binary_op(Long a, Object o, int binary_op) {
        if (o.getClass() != Long.class) return null;
        var b = ((Long) o);
        return switch (binary_op) {
            case BINOP_ADD -> a + b;
            case BINOP_SUBTRACT -> a - b;
            case BINOP_MULTIPLY -> a * b;
            case BINOP_DIVIDE -> a.doubleValue() / b.doubleValue();
            case BINOP_FLOORDIV -> a / b;
            case BINOP_MOD -> a % b;
            case BINOP_DIVMOD -> new Object[]{a / b, a % b};
            case BINOP_POW -> Math.pow(a, b);
            case BINOP_LSHIFT -> a << b;
            case BINOP_RSHIFT -> a >> b;
            case BINOP_AND -> a & b;
            case BINOP_OR -> a | b;
            case BINOP_XOR -> a ^ b;
            default -> null;
        };
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

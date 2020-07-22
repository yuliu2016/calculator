package org.fugalang.core.eval;

import org.fugalang.core.object.FObject;
import org.fugalang.core.object.type.FDescriptor;
import org.fugalang.core.object.type.FType;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.fugalang.core.object.FConst.*;
import static org.fugalang.core.opcode.BinaryOp.BINOP_ADD;
import static org.fugalang.core.opcode.BinaryOp.BINOP_MULTIPLY;
import static org.fugalang.core.opcode.CompareOp.*;
import static org.fugalang.core.opcode.UnaryOp.*;

@SuppressWarnings("unused")
public class FAbstract {
    @SuppressWarnings("unchecked")
    public static FType<Object> typeOf(Object o) {
        return (FType<Object>) rawTypeOf(o);
    }

    private static FType<?> rawTypeOf(Object o) {
        var cls = o.getClass();
        if (cls == FObject.class) return ((FObject<?>) o).type;
        if (cls == Long.class) return LongType;
        if (cls == Double.class) return FloatType;
        if (cls == String.class) return StringType;
        if (cls == FDescriptor.class) return MetaType;
        if (o instanceof List) return ListType;
        if (o instanceof Map) return MapType;
        if (o instanceof Set) return SetType;
        if (o instanceof Object[]) return TupleType;
        return null;
    }

    public static boolean isTrue(Object o) {
        if (o == null) return false;
        if (o.getClass() == Boolean.class) return (boolean) o;
        var r = unary_op(o, UNARY_BOOL);
        return r == null || r.getClass() != Boolean.class || (boolean) r;
    }

    public static Boolean compareOp(int cmp_op, int cmp_result) {
        switch (cmp_op) {
            case CMP_LT:
                return cmp_result < 0;
            case CMP_LE:
                return cmp_result <= 0;
            case CMP_EQ:
                return cmp_result == 0;
            case CMP_NE:
                return cmp_result != 0;
            case CMP_GT:
                return cmp_result > 0;
            case CMP_GE:
                return cmp_result >= 0;
            default:
                return null;
        }
    }

    public static Object compare(Object a, Object b, int cmp_op) {
        return typeOf(a).compare_op(a, b, cmp_op);
    }

    public static boolean equals(Object a, Object b) {
        if (a == b) return true;
        return isTrue(compare(a, b, CMP_EQ));
    }

    public static <T> boolean equals(FType<T> type, T value, Object obj) {
        if (value == obj) return true;
        return isTrue(type.compare_op(value, obj, CMP_EQ));
    }

    public static Object unary_op(Object a, int unary_op) {
        return typeOf(a).unary_op(a, unary_op);
    }

    public static Object binary_op(Object a, Object b, int binary_op) {
        return typeOf(a).binary_op(a, b, binary_op);
    }

    public static Object rh_binary_op(Object a, Object b, int binary_op) {
        return typeOf(a).rh_binary_op(a, b, binary_op);
    }

    public static Object add(Object a, Object b) {
        var v = binary_op(a, b, BINOP_ADD);
        return v != null ? v : rh_binary_op(b, a, BINOP_ADD);
    }

    public static Object multiply(Object a, Object b) {
        var v = binary_op(a, b, BINOP_MULTIPLY);
        return v != null ? v : rh_binary_op(b, a, BINOP_MULTIPLY);
    }

    public static <T> String toString(FType<T> type, T value) {
        var str = type.unary_op(value, UNARY_STR);
        return (str == null || str.getClass() != String.class) ?
                value.toString() : (String) str;
    }

    public static <T> int hashCode(FType<T> type, T value) {
        var hash = type.unary_op(value, UNARY_HASH);
        return (hash == null || hash.getClass() != Integer.class) ?
                value.hashCode() : (int) hash;
    }
}

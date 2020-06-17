package org.fugalang.core.eval;

import org.fugalang.core.object.FObject;
import org.fugalang.core.object.FType;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.fugalang.core.object.FConst.*;
import static org.fugalang.core.opcode.CompareOp.*;
import static org.fugalang.core.opcode.UnaryOp.UNARY_BOOL;

public class FEval {
    @SuppressWarnings("unchecked")
    public static FType<Object> typeOfObject(Object o) {
        return (FType<Object>) typeOfRaw(o);
    }

    public static FType<?> typeOfRaw(Object o) {
        var cls = o.getClass();
        if (cls == FObject.class) return ((FObject) o).type;
        if (cls == BigInteger.class) return LongType;
        if (cls == Double.class) return FloatType;
        if (cls == String.class) return StringType;
        if (o instanceof List) return ListType;
        if (o instanceof Map) return MapType;
        if (o instanceof Set) return SetType;
        if (o instanceof Object[]) return TupleType;
        return null;
    }

    public static boolean isTrue(Object o) {
        if (o == null) return false;
        var cls = o.getClass();
        if (cls == Boolean.class) return (boolean) o;
        var r = typeOfObject(o).unary_op(o, UNARY_BOOL);
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
            case CMP_IN:
            case CMP_NOT_IN:
            case CMP_IS:
            case CMP_IS_NOT:
            default:
                return null;
        }
    }

    public static Object compare(Object a, Object b, int cmp_op) {
        return typeOfObject(a).compare_op(a, b, cmp_op);
    }

    /**
     * Check if two objects are equal
     * Use this instead of {@link Object#equals(Object)}
     */
    public static boolean isEqual(Object a, Object b) {
        return isTrue(compare(a, b, CMP_EQ));
    }
}

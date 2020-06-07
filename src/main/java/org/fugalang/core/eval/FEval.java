package org.fugalang.core.eval;

import org.fugalang.core.object.FObject;
import org.fugalang.core.object.FType;

import java.math.BigInteger;

import static org.fugalang.core.object.FConst.*;

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
        if (o instanceof Object[]) return TupleType;
        return null;
    }

    public static boolean isTrue(Object o) {
        if (o == null) return false;
        var cls = o.getClass();
        if (cls == Boolean.class) return (boolean) o;
        var r = typeOfObject(o).__bool__(o);
        return r == null || r.getClass() != Boolean.class || (boolean) r;
    }

    public static Object compareLt(Object a, Object b) {
        return typeOfObject(a).__lt__(a, b);
    }

    public static boolean compareLtIsTrue(Object a, Object b) {
        return isTrue(compareLt(a, b));
    }

    public static Object compareLe(Object a, Object b) {
        return typeOfObject(a).__le__(a, b);
    }

    public static boolean compareLeIsTrue(Object a, Object b) {
        return isTrue(compareLe(a, b));
    }

    public static Object compareEq(Object a, Object b) {
        return typeOfObject(a).__eq__(a, b);
    }

    public static boolean compareEqIsTrue(Object a, Object b) {
        return isTrue(compareEq(a, b));
    }

    public static Object compareNe(Object a, Object b) {
        return typeOfObject(a).__ne__(a, b);
    }

    public static boolean compareNeIsTrue(Object a, Object b) {
        return isTrue(compareNe(a, b));
    }

    public static Object compareGt(Object a, Object b) {
        return typeOfObject(a).__gt__(a, b);
    }

    public static boolean compareGtIsTrue(Object a, Object b) {
        return isTrue(compareGt(a, b));
    }

    public static Object compareGe(Object a, Object b) {
        return typeOfObject(a).__ge__(a, b);
    }

    public static boolean compareGeIsTrue(Object a, Object b) {
        return isTrue(compareGe(a, b));
    }
}

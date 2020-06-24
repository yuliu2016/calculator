package org.fugalang.core.object;

import org.fugalang.core.eval.FAbstract;
import org.fugalang.core.object.type.FMetaType;
import org.fugalang.core.object.type.FProxyType;
import org.fugalang.core.object.type.FType;

@SuppressWarnings({"EqualsWhichDoesntCheckParameterClass", "unused"})
public final class FObject<T> {

    public final FType<T> type;
    public final T value;

    private FObject(FType<T> type, T value) {
        this.type = type;
        this.value = value;
    }

    public FType<T> getType() {
        return type;
    }

    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return FAbstract.toString(type, value);
    }

    @Override
    public int hashCode() {
        return FAbstract.hashCode(type, value);
    }

    @Override
    public boolean equals(Object obj) {
        return FAbstract.equals(type, value, obj);
    }

    public static <T> FObject<T> fromType(FType<T> type, T value) {
        return new FObject<>(type, value);
    }

    public static <T> FObject<T> fromType(FMetaType metaType, T value) {
        return new FObject<>(new FProxyType<>(metaType), value);
    }
}

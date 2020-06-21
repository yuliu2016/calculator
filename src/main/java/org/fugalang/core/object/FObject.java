package org.fugalang.core.object;

import org.fugalang.core.eval.FAbstract;
import org.fugalang.core.object.type.FType;

@SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
public final class FObject<T> {

    public final FType<T> type;
    public final T value;

    public FObject(FType<T> type, T value) {
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
}

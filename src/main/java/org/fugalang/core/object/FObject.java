package org.fugalang.core.object;

import org.fugalang.core.eval.FEval;
import org.fugalang.core.opcode.CompareOp;

public final class FObject {

    public final FType<Object> type;
    public final Object value;

    public FObject(FType<Object> type, Object ob_value) {
        this.type = type;
        this.value = ob_value;
    }

    public FType<Object> getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        String str = (String) type.__str__(value);
        return str == null ? super.toString() : str;
    }

    @Override
    public int hashCode() {
        Integer hash = (Integer) type.__hash__(value);
        return hash == null ? super.hashCode() : hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj.getClass() == FObject.class)) return false;
        return FEval.isTrue(type.compare_op(value, obj, CompareOp.CMP_EQ));
    }
}

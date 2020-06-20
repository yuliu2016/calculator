package org.fugalang.core.object;

import org.fugalang.core.eval.FEval;
import org.fugalang.core.opcode.CompareOp;
import org.fugalang.core.opcode.UnaryOp;

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
        var str = type.unary_op(value, UnaryOp.UNARY_STR);
        return (str == null || str.getClass() != String.class) ?
                super.toString() : (String) str;
    }

    @Override
    public int hashCode() {
        var hash = type.unary_op(value, UnaryOp.UNARY_HASH);
        return (hash == null || hash.getClass() != Integer.class) ?
                super.hashCode() : (int) hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj.getClass() == Object.class)) return false;
        var eq = type.compare_op(value, obj, CompareOp.CMP_EQ);
        return FEval.isTrue(eq);
    }
}

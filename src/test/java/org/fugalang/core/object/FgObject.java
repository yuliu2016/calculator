package org.fugalang.core.object;

import org.fugalang.core.object.type.FgType;

/**
 * The base Fuga Object class
 */
@Deprecated
public class FgObject {
    public final FgType ob_type;

    public FgObject(FgType ob_type) {
        this.ob_type = ob_type;
    }

    @Override
    public String toString() {
        if (ob_type.tp_repr != null) {
            return ob_type.tp_repr.call(this).toString();
        } else if (ob_type.tp_str != null) {
            return ob_type.tp_str.call(this).toString();
        }
        return ob_type.tp_name + "@" + System.identityHashCode(this);
    }
}

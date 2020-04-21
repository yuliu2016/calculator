package org.fugalang.core.object;

import org.fugalang.core.object.type.FgNumberMethods;
import org.fugalang.core.object.type.FgType;

public class FgInt extends FgObject {

    private final int value;

    public FgInt(int value) {
        super(INT_TYPE);
        this.value = value;
    }

    public FgInt() {
        this(0);
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    public static FgObject Int_Add(FgObject first, FgObject second) {
        return new FgInt(0);
    }

    public static FgObject Int_Str(FgObject object) {
        if (object instanceof FgInt) {
            return null;
        }
        return null;
    }

    private static final FgNumberMethods NUMBER_METHODS = new FgNumberMethods(
            FgInt::Int_Add,               // nb_add
            null,               // nb_subtract
            null,               // nb_multiply
            null,               // nb_remainder
            null,               // nb_divmod
            null,               // nb_power
            null,               // nb_negative
            null,               // nb_positive
            null,               // nb_absolute
            null,               // nb_bool
            null,               // nb_invert
            null,               // nb_lshift
            null,               // nb_rshift
            null,               // nb_and
            null,               // nb_xor
            null,               // nb_or
            null,               // nb_int
            null,               // nb_float
            null,               // nb_inplace_add
            null,               // nb_inplace_subtract
            null,               // nb_inplace_multiply
            null,               // nb_inplace_remainder
            null,               // nb_inplace_power
            null,               // nb_inplace_lshift
            null,               // nb_inplace_rshift
            null,               // nb_inplace_and
            null,               // nb_inplace_xor
            null,               // nb_inplace_or
            null,               // nb_floor_divide
            null,               // nb_true_divide
            null,               // nb_inplace_floor_divide
            null,               // nb_inplace_true_divide
            null,               // nb_index
            null,               // nb_matrix_multiply
            null
    );

    private static final FgType INT_TYPE = new FgType(
            "IntType",
            NUMBER_METHODS,
            null,
            null,
            FgInt::Int_Str,
            FgInt::Int_Str,
            null,
            null
    );
}

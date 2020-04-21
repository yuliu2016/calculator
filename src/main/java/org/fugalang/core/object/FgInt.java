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
        return Int_Str(this).getValue();
    }

    public static FgObject Int_Add(FgObject first, FgObject second) {
        return new FgInt(0);
    }

    public static FgObject Int_Subtract(FgObject first, FgObject second) {
        return new FgInt(0);
    }

    public static FgObject Int_Multiply(FgObject first, FgObject second) {
        return new FgInt(0);
    }

    public static FgObject Int_Remainder(FgObject first, FgObject second) {
        return new FgInt(0);
    }

    public static FgObject Int_DivMod(FgObject first, FgObject second) {
        return new FgInt(0);
    }

    public static FgObject Int_Power(FgObject first, FgObject second, FgObject third) {
        return new FgInt(0);
    }

    public static FgObject Int_Negative(FgObject first) {
        return new FgInt(0);
    }

    public static FgObject Int_Positive(FgObject first) {
        return new FgInt(0);
    }

    public static FgObject Int_Absolute(FgObject first) {
        return new FgInt(0);
    }

    public static int Int_Bool(FgObject first) {
        return ((FgInt) first).value == 0 ? 0 : 1;
    }

    public static FgObject Int_Invert(FgObject first) {
        return new FgInt(-((FgInt) first).value);
    }

    public static FgObject Int_LShift(FgObject first, FgObject second) {
        return new FgInt(0);
    }

    public static FgObject Int_RShift(FgObject first, FgObject second) {
        return new FgInt(0);
    }

    public static FgInt Int_ToInt(FgObject object) {
        return ((FgInt) object);
    }

    public static FgString Int_Str(FgObject object) {
        return new FgString(Integer.toString(((FgInt) object).value));
    }

    private static final FgNumberMethods NUMBER_METHODS = new FgNumberMethods(
            FgInt::Int_Add,               // nb_add
            FgInt::Int_Subtract,               // nb_subtract
            FgInt::Int_Multiply,               // nb_multiply
            FgInt::Int_Remainder,               // nb_remainder
            FgInt::Int_DivMod,               // nb_divmod
            FgInt::Int_Power,               // nb_power
            FgInt::Int_Negative,               // nb_negative
            FgInt::Int_Positive,               // nb_positive
            FgInt::Int_Absolute,               // nb_absolute
            FgInt::Int_Bool,               // nb_bool
            FgInt::Int_Invert,               // nb_invert
            FgInt::Int_LShift,               // nb_lshift
            FgInt::Int_RShift,               // nb_rshift
            null,               // nb_and
            null,               // nb_xor
            null,               // nb_or
            FgInt::Int_ToInt,               // nb_int
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

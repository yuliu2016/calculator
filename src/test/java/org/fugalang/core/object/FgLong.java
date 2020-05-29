package org.fugalang.core.object;

import org.fugalang.core.object.type.FgNumberMethods;
import org.fugalang.core.object.type.FgType;

import java.math.BigInteger;

@Deprecated
public class FgLong extends FgObject {

    // todo support small ints

    private BigInteger value;

    public FgLong(BigInteger value) {
        super(FG_LONG_TYPE);
        this.value = value;
    }

    public FgLong(int value) {
        this(BigInteger.valueOf(value));
    }

    @Override
    public String toString() {
        return Long_Str(this).getValue();
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private static boolean CHECK_BINOP(FgObject a, FgObject b) {
        return a.ob_type == FG_LONG_TYPE && b.ob_type == FG_LONG_TYPE;
    }

    private static boolean CHECK_TYPE(FgObject a) {
        return a.ob_type == FG_LONG_TYPE;
    }

    public static FgObject Long_Add(FgObject a, FgObject b) {
        FgLong result;

        if (!CHECK_BINOP(a, b)) {
            throw new UnsupportedOperationException();
        }

        BigInteger sum = ((FgLong) a).value.add(((FgLong) b).value);
        result = new FgLong(sum);

        return result;
    }

    public static FgObject Long_Subtract(FgObject a, FgObject b) {
        FgLong result;

        if (!CHECK_BINOP(a, b)) {
            throw new UnsupportedOperationException();
        }

        BigInteger diff = ((FgLong) a).value.subtract(((FgLong) b).value);
        result = new FgLong(diff);

        return result;
    }

    public static FgObject Long_Multiply(FgObject a, FgObject b) {
        FgLong result;

        if (!CHECK_BINOP(a, b)) {
            throw new UnsupportedOperationException();
        }

        BigInteger prod = ((FgLong) a).value.multiply(((FgLong) b).value);
        result = new FgLong(prod);

        return result;
    }

    public static FgObject Long_Remainder(FgObject a, FgObject b) {
        FgLong result;

        if (!CHECK_BINOP(a, b)) {
            throw new UnsupportedOperationException();
        }

        BigInteger rem = ((FgLong) a).value.remainder(((FgLong) b).value);
        result = new FgLong(rem);

        return result;
    }

    public static FgObject Long_DivMod(FgObject a, FgObject b) {

        if (!CHECK_BINOP(a, b)) {
            throw new UnsupportedOperationException();
        }

        throw new UnsupportedOperationException();
    }

    public static FgObject Long_Power(FgObject a, FgObject b, FgObject c) {
        BigInteger z;

        if (!CHECK_BINOP(a, b)) {
            throw new UnsupportedOperationException();
        }

        if (CHECK_TYPE(c)) {
            z = ((FgLong) c).value;
        } else {
            // todo check if mod is None
            z = null;
        }

        var x = ((FgLong) a).value;
        var y = ((FgLong) b).value;

        if (z == null) {
            return new FgLong(x.pow(y.intValueExact()));
        } else {
            return new FgLong(x.modPow(y, z));
        }
    }

    public static FgObject Long_Negative(FgObject a) {
        FgLong result;

        if (!CHECK_TYPE(a)) {
            throw new UnsupportedOperationException();
        }

        BigInteger neg = ((FgLong) a).value.negate();
        result = new FgLong(neg);

        return result;
    }

    public static FgObject Long_Positive(FgObject a) {
        if (!CHECK_TYPE(a)) {
            throw new UnsupportedOperationException();
        }

        return a;
    }

    public static FgObject Long_Absolute(FgObject a) {
        FgLong result;

        if (!CHECK_TYPE(a)) {
            throw new UnsupportedOperationException();
        }

        BigInteger x = ((FgLong) a).value;
        result = new FgLong(x.signum() >= 0 ? x : x.abs());

        return result;
    }

    public static int Long_Bool(FgObject a) {
        return ((FgLong) a).value.signum() == 0 ? 0 : 1;
    }

    public static FgObject Long_Invert(FgObject a) {
        FgLong result;

        if (!CHECK_TYPE(a)) {
            throw new UnsupportedOperationException();
        }

        BigInteger neg = ((FgLong) a).value.not();
        result = new FgLong(neg);

        return result;
    }

    public static FgObject Long_LShift(FgObject a, FgObject b) {
        FgLong result;

        if (!CHECK_BINOP(a, b)) {
            throw new UnsupportedOperationException();
        }

        BigInteger sum = ((FgLong) a).value.shiftLeft(((FgLong) b).value.intValueExact());
        result = new FgLong(sum);

        return result;
    }

    public static FgObject Long_RShift(FgObject a, FgObject b) {
        FgLong result;

        if (!CHECK_BINOP(a, b)) {
            throw new UnsupportedOperationException();
        }

        BigInteger sum = ((FgLong) a).value.shiftRight(((FgLong) b).value.intValueExact());
        result = new FgLong(sum);

        return result;
    }

    public static FgObject Long_And(FgObject a, FgObject b) {
        FgLong result;

        if (!CHECK_BINOP(a, b)) {
            throw new UnsupportedOperationException();
        }

        BigInteger r = ((FgLong) a).value.and(((FgLong) b).value);
        result = new FgLong(r);

        return result;
    }

    public static FgObject Long_Xor(FgObject a, FgObject b) {
        FgLong result;

        if (!CHECK_BINOP(a, b)) {
            throw new UnsupportedOperationException();
        }

        BigInteger r = ((FgLong) a).value.xor(((FgLong) b).value);
        result = new FgLong(r);

        return result;
    }

    public static FgObject Long_Or(FgObject a, FgObject b) {
        FgLong result;

        if (!CHECK_BINOP(a, b)) {
            throw new UnsupportedOperationException();
        }

        BigInteger r = ((FgLong) a).value.or(((FgLong) b).value);
        result = new FgLong(r);

        return result;
    }

    public static FgObject Long_Float(FgObject a) {
        if (!CHECK_TYPE(a)) {
            throw new UnsupportedOperationException();
        }

        throw new UnsupportedOperationException();
    }


    public static FgObject Long_Inplace_Add(FgObject a, FgObject b) {
        if (!CHECK_BINOP(a, b)) {
            throw new UnsupportedOperationException();
        }

        var x = ((FgLong) a);
        x.value = x.value.add(((FgLong) b).value);
        return a;
    }

    public static FgObject Long_Inplace_Subtract(FgObject a, FgObject b) {
        if (!CHECK_BINOP(a, b)) {
            throw new UnsupportedOperationException();
        }

        var x = ((FgLong) a);
        x.value = x.value.subtract(((FgLong) b).value);
        return a;
    }

    public static FgObject Long_Inplace_Multiply(FgObject a, FgObject b) {
        if (!CHECK_BINOP(a, b)) {
            throw new UnsupportedOperationException();
        }

        var x = ((FgLong) a);
        x.value = x.value.multiply(((FgLong) b).value);
        return a;
    }

    public static FgObject Long_Inplace_Remainder(FgObject a, FgObject b) {
        if (!CHECK_BINOP(a, b)) {
            throw new UnsupportedOperationException();
        }

        var x = ((FgLong) a);
        x.value = x.value.remainder(((FgLong) b).value);
        return a;
    }

    public static FgObject Long_Inplace_Power(FgObject a, FgObject b, FgObject c) {
        BigInteger z;

        if (!CHECK_BINOP(a, b)) {
            throw new UnsupportedOperationException();
        }

        if (CHECK_TYPE(c)) {
            z = ((FgLong) c).value;
        } else {
            // todo check if mod is None
            z = null;
        }

        var x = (FgLong) a;
        var y = ((FgLong) b).value;

        if (z == null) {
            x.value = x.value.pow(y.intValueExact());
        } else {
            x.value = x.value.modPow(y, z);
        }

        return a;
    }

    public static FgObject Long_Inplace_LShift(FgObject a, FgObject b) {
        if (!CHECK_BINOP(a, b)) {
            throw new UnsupportedOperationException();
        }

        var x = ((FgLong) a);
        x.value = x.value.shiftLeft(((FgLong) b).value.intValueExact());
        return a;
    }

    public static FgObject Long_Inplace_RShift(FgObject a, FgObject b) {
        if (!CHECK_BINOP(a, b)) {
            throw new UnsupportedOperationException();
        }

        var x = ((FgLong) a);
        x.value = x.value.shiftRight(((FgLong) b).value.intValueExact());
        return a;
    }

    public static FgObject Long_Inplace_And(FgObject a, FgObject b) {
        if (!CHECK_BINOP(a, b)) {
            throw new UnsupportedOperationException();
        }

        var x = ((FgLong) a);
        x.value = x.value.and(((FgLong) b).value);
        return a;
    }

    public static FgObject Long_Inplace_Xor(FgObject a, FgObject b) {
        if (!CHECK_BINOP(a, b)) {
            throw new UnsupportedOperationException();
        }

        var x = ((FgLong) a);
        x.value = x.value.xor(((FgLong) b).value);
        return a;
    }

    public static FgObject Long_Inplace_Or(FgObject a, FgObject b) {
        if (!CHECK_BINOP(a, b)) {
            throw new UnsupportedOperationException();
        }

        var x = ((FgLong) a);
        x.value = x.value.or(((FgLong) b).value);
        return a;
    }


    public static FgObject Long_Floor_Divide(FgObject a, FgObject b) {
        FgLong result;

        if (!CHECK_BINOP(a, b)) {
            throw new UnsupportedOperationException();
        }

        BigInteger quot = ((FgLong) a).value.divide(((FgLong) b).value);
        result = new FgLong(quot);

        return result;
    }

    public static FgObject Long_True_Divide(FgObject a, FgObject b) {
        if (!CHECK_BINOP(a, b)) {
            throw new UnsupportedOperationException();
        }

        throw new UnsupportedOperationException();
    }

    public static FgObject Long_Inplace_Floor_Divide(FgObject a, FgObject b) {
        if (!CHECK_BINOP(a, b)) {
            throw new UnsupportedOperationException();
        }

        var x = ((FgLong) a);
        x.value = x.value.divide(((FgLong) b).value);
        return a;
    }

    public static FgObject Long_Inplace_True_Divide(FgObject a, FgObject b) {
        if (!CHECK_BINOP(a, b)) {
            throw new UnsupportedOperationException();
        }

        throw new UnsupportedOperationException();
    }

    public static FgObject Long_ToInt(FgObject a) {
        if (!CHECK_TYPE(a)) {
            throw new UnsupportedOperationException();
        }
        return a;
    }

    public static FgString Long_Str(FgObject object) {
        return new FgString(((FgLong) object).value.toString());
    }

    private static final FgNumberMethods NUMBER_METHODS = new FgNumberMethods(
            FgLong::Long_Add,                  // nb_add
            FgLong::Long_Subtract,             // nb_subtract
            FgLong::Long_Multiply,             // nb_multiply
            FgLong::Long_Remainder,            // nb_remainder
            FgLong::Long_DivMod,               // nb_divmod
            FgLong::Long_Power,                // nb_power
            FgLong::Long_Negative,             // nb_negative
            FgLong::Long_Positive,             // nb_positive
            FgLong::Long_Absolute,             // nb_absolute
            FgLong::Long_Bool,                 // nb_bool
            FgLong::Long_Invert,               // nb_invert
            FgLong::Long_LShift,               // nb_lshift
            FgLong::Long_RShift,               // nb_rshift
            FgLong::Long_And,                  // nb_and
            FgLong::Long_Xor,                  // nb_xor
            FgLong::Long_Or,                   // nb_or
            FgLong::Long_ToInt,                // nb_int
            FgLong::Long_Float,                // nb_float
            FgLong::Long_Inplace_Add,          // nb_inplace_add
            FgLong::Long_Inplace_Subtract,     // nb_inplace_subtract
            FgLong::Long_Inplace_Multiply,     // nb_inplace_multiply
            FgLong::Long_Inplace_Remainder,    // nb_inplace_remainder
            FgLong::Long_Inplace_Power,        // nb_inplace_power
            FgLong::Long_Inplace_LShift,       // nb_inplace_lshift
            FgLong::Long_Inplace_RShift,       // nb_inplace_rshift
            FgLong::Long_Inplace_And,          // nb_inplace_and
            FgLong::Long_Inplace_Xor,          // nb_inplace_xor
            FgLong::Long_Inplace_Or,           // nb_inplace_or
            FgLong::Long_Floor_Divide,         // nb_floor_divide
            FgLong::Long_True_Divide,          // nb_true_divide
            FgLong::Long_Inplace_Floor_Divide, // nb_inplace_floor_divide
            FgLong::Long_Inplace_True_Divide,  // nb_inplace_true_divide
            null,
            null,
            null
    );

    private static final FgType FG_LONG_TYPE = new FgType(
            "LongType",
            NUMBER_METHODS,
            null,
            null,
            FgLong::Long_Str,
            FgLong::Long_Str,
            null,
            null
    );
}

package org.fugalang.core.object.type;

import static org.fugalang.core.object.type.FgTypeFunc.*;

/**
 * Number suite
 */
public class FgNumberMethods {

    public final BinaryFunc  nb_add;
    public final BinaryFunc  nb_subtract;
    public final BinaryFunc  nb_multiply;
    public final BinaryFunc  nb_remainder;
    public final BinaryFunc  nb_divmod;
    public final TerneryFunc nb_power;
    public final UnaryFunc   nb_negative;
    public final UnaryFunc   nb_positive;
    public final UnaryFunc   nb_absolute;
    public final InquiryFunc nb_bool;
    public final UnaryFunc   nb_invert;
    public final BinaryFunc  nb_lshift;
    public final BinaryFunc  nb_rshift;
    public final BinaryFunc  nb_and;
    public final BinaryFunc  nb_xor;
    public final BinaryFunc  nb_or;
    public final UnaryFunc   nb_int;
    public final UnaryFunc   nb_float;

    public final BinaryFunc  nb_inplace_add;
    public final BinaryFunc  nb_inplace_subtract;
    public final BinaryFunc  nb_inplace_multiply;
    public final BinaryFunc  nb_inplace_remainder;
    public final TerneryFunc nb_inplace_power;
    public final BinaryFunc  nb_inplace_lshift;
    public final BinaryFunc  nb_inplace_rshift;
    public final BinaryFunc  nb_inplace_and;
    public final BinaryFunc  nb_inplace_xor;
    public final BinaryFunc  nb_inplace_or;

    public final BinaryFunc  nb_floor_divide;
    public final BinaryFunc  nb_true_divide;
    public final BinaryFunc  nb_inplace_floor_divide;
    public final BinaryFunc  nb_inplace_true_divide;

    public final UnaryFunc   nb_index;

    public final BinaryFunc  nb_matrix_multiply;
    public final BinaryFunc  nb_inplace_matrix_multiply;

    public FgNumberMethods(
            BinaryFunc nb_add,
            BinaryFunc nb_subtract,
            BinaryFunc nb_multiply,
            BinaryFunc nb_remainder,
            BinaryFunc nb_divmod,
            TerneryFunc nb_power,
            UnaryFunc nb_negative,
            UnaryFunc nb_positive,
            UnaryFunc nb_absolute,
            InquiryFunc nb_bool,
            UnaryFunc nb_invert,
            BinaryFunc nb_lshift,
            BinaryFunc nb_rshift,
            BinaryFunc nb_and,
            BinaryFunc nb_xor,
            BinaryFunc nb_or,
            UnaryFunc nb_int,
            UnaryFunc nb_float,
            BinaryFunc nb_inplace_add,
            BinaryFunc nb_inplace_subtract,
            BinaryFunc nb_inplace_multiply,
            BinaryFunc nb_inplace_remainder,
            TerneryFunc nb_inplace_power,
            BinaryFunc nb_inplace_lshift,
            BinaryFunc nb_inplace_rshift,
            BinaryFunc nb_inplace_and,
            BinaryFunc nb_inplace_xor,
            BinaryFunc nb_inplace_or,
            BinaryFunc nb_floor_divide,
            BinaryFunc nb_true_divide,
            BinaryFunc nb_inplace_floor_divide,
            BinaryFunc nb_inplace_true_divide,
            UnaryFunc nb_index,
            BinaryFunc nb_matrix_multiply,
            BinaryFunc nb_inplace_matrix_multiply
    ) {
        this.nb_add = nb_add;
        this.nb_subtract = nb_subtract;
        this.nb_multiply = nb_multiply;
        this.nb_remainder = nb_remainder;
        this.nb_divmod = nb_divmod;
        this.nb_power = nb_power;
        this.nb_negative = nb_negative;
        this.nb_positive = nb_positive;
        this.nb_absolute = nb_absolute;
        this.nb_bool = nb_bool;
        this.nb_invert = nb_invert;
        this.nb_lshift = nb_lshift;
        this.nb_rshift = nb_rshift;
        this.nb_and = nb_and;
        this.nb_xor = nb_xor;
        this.nb_or = nb_or;
        this.nb_int = nb_int;
        this.nb_float = nb_float;
        this.nb_inplace_add = nb_inplace_add;
        this.nb_inplace_subtract = nb_inplace_subtract;
        this.nb_inplace_multiply = nb_inplace_multiply;
        this.nb_inplace_remainder = nb_inplace_remainder;
        this.nb_inplace_power = nb_inplace_power;
        this.nb_inplace_lshift = nb_inplace_lshift;
        this.nb_inplace_rshift = nb_inplace_rshift;
        this.nb_inplace_and = nb_inplace_and;
        this.nb_inplace_xor = nb_inplace_xor;
        this.nb_inplace_or = nb_inplace_or;
        this.nb_floor_divide = nb_floor_divide;
        this.nb_true_divide = nb_true_divide;
        this.nb_inplace_floor_divide = nb_inplace_floor_divide;
        this.nb_inplace_true_divide = nb_inplace_true_divide;
        this.nb_index = nb_index;
        this.nb_matrix_multiply = nb_matrix_multiply;
        this.nb_inplace_matrix_multiply = nb_inplace_matrix_multiply;
    }
}

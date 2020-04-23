package org.fugalang.core.compile;

@SuppressWarnings("unused")
public class Bytecode {
    public static final byte                 NOP =  0;
    public static final byte             POP_TOP =  1;
    public static final byte             ROT_TWO =  2;
    public static final byte           ROT_THREE =  3;
    public static final byte            ROT_FOUR =  4;
    public static final byte             DUP_TOP =  5;
    public static final byte         DUP_TOP_TWO =  6;
    public static final byte      UNARY_POSITIVE =  7;
    public static final byte      UNARY_NEGATIVE =  8;
    public static final byte           UNARY_NOT =  9;
    public static final byte        UNARY_INVERT = 10;
    public static final byte        BINARY_POWER = 11;
    public static final byte     BINARY_MULTIPLY = 12;
    public static final byte BINARY_FLOOR_DIVIDE = 13;
    public static final byte  BINARY_TRUE_DIVIDE = 14;
    public static final byte       BINARY_MODULO = 15;
    public static final byte          BINARY_ADD = 16;
    public static final byte     BINARY_SUBTRACT = 17;
    public static final byte       BINARY_SUBSCR = 18;
    public static final byte       BINARY_LSHIFT = 19;
    public static final byte       BINARY_RSHIFT = 20;
    public static final byte          BINARY_AND = 21;
    public static final byte          BINARY_XOR = 22;
    public static final byte           BINARY_OR = 23;
    public static final byte          PRINT_EXPR = 24;
    public static final byte          LOAD_CONST = 25;
}

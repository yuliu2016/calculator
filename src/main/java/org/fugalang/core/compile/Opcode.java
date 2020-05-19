package org.fugalang.core.compile;

@SuppressWarnings("unused")
public class Opcode {
    // STACK OPERATIONS
    public static final byte                 NOP =  1;
    public static final byte             POP_TOP =  2;
    public static final byte             ROT_TWO =  3;
    public static final byte           ROT_THREE =  4;
    public static final byte            ROT_FOUR =  5;
    public static final byte             DUP_TOP =  6;
    public static final byte         DUP_TOP_TWO =  7;

    // LOAD/STORE

    public static final byte            LOAD_CONST =  8;
    public static final byte            LOAD_NAME =  8;
    public static final byte            LOAD_FAST =  8;
    public static final byte            LOAD_ATTR =  8;
    public static final byte            STORE_ITEM =  8;
    public static final byte            STORE_ =  8;
    public static final byte            UNARY_OP =  8;
    public static final byte            RETURN_VALUE =  8;

    public static final byte           UNARY_NOT =  9;
    public static final byte           BINARY_OP = 11;
    public static final byte          INPLACE_OP = 11;
    public static final byte          PRINT_EXPR = 24;

    private Opcode() {
    }
}

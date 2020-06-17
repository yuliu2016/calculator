package org.fugalang.core.opcode;

@SuppressWarnings("unused")
public class FrameOp {
    // STACK OPERATIONS
    public static final int                 NOP =  1;
    public static final int             POP_TOP =  2;
    public static final int             ROT_TWO =  3;
    public static final int           ROT_THREE =  4;
    public static final int            ROT_FOUR =  5;
    public static final int             DUP_TOP =  6;
    public static final int         DUP_TOP_TWO =  7;

    // LOAD/STORE

    public static final int            LOAD_CONST =  8;
    public static final int            LOAD_NAME =  8;
    public static final int            LOAD_FAST =  8;
    public static final int            LOAD_ATTR =  8;
    public static final int            STORE_ITEM =  8;
    public static final int            STORE_ =  8;
    public static final int            UNARY_OP =  8;
    public static final int            RETURN_VALUE =  8;

    public static final int           UNARY_NOT =  9;
    public static final int           BINARY_OP = 11;
    public static final int          INPLACE_OP = 11;
    public static final int          PRINT_EXPR = 24;

    private FrameOp() {
    }
}

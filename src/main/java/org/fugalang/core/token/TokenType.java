package org.fugalang.core.token;

import java.util.List;

public enum TokenType {
    NEWLINE,
    SPACE,
    KEYWORD,
    SYMBOL,
    OPERATOR,
    INTEGER,
    LONG_INT,
    FLOAT,
    COMPLEX,
    STRING,
    F_STR_NEW,
    F_STR_END,
    BOOLEAN,
    NONE,
    DOC_NEW,
    DOC_STR,
    DOC_REF,
    DOC_END;

    public static final List<TokenType> DELIMITERS =
            List.of(NEWLINE, SPACE, DOC_NEW, DOC_END, F_STR_NEW, F_STR_END);

    public static final List<TokenType> NUMBER_LITERALS =
            List.of(INTEGER, LONG_INT, FLOAT, COMPLEX);

    public static final List<TokenType> SYMBOLS =
            List.of(SYMBOL, DOC_REF);
}

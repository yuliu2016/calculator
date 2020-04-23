package org.fugalang.core.token;

import java.util.List;

public enum TokenType {
    NEWLINE,
    KEYWORD,
    SYMBOL,
    OPERATOR,
    INTEGER,
    LONG_INT,
    FLOAT,
    COMPLEX,
    STRING,
    BOOLEAN,
    NONE;

    public static final List<TokenType> DELIMITERS =
            List.of(NEWLINE);

    public static final List<TokenType> NUMBER_LITERALS =
            List.of(INTEGER, LONG_INT, FLOAT, COMPLEX);

    public static final List<TokenType> SYMBOLS =
            List.of(SYMBOL);
}

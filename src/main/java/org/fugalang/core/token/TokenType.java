package org.fugalang.core.token;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum TokenType {
    ENDMARKER,
    NEWLINE,
    KEYWORD,
    NAME,
    OPERATOR,
    NUMBER,
    STRING,
    BOOLEAN,
    NONE;

    public static final List<TokenType> DELIMITERS =
            List.of(NEWLINE);

    public static final List<TokenType> NUMBER_LITERALS =
            List.of(NUMBER);

    public static final List<TokenType> SYMBOLS =
            List.of(NAME);

    public static final List<String> NAMES =
            Arrays.stream(values()).map(Enum::name).collect(Collectors.toList());
}

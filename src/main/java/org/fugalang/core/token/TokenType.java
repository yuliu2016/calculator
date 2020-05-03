package org.fugalang.core.token;

import org.fugalang.core.parser.ElementType;

import java.util.List;
import java.util.stream.Collectors;

public class TokenType {

    public static final ElementType ENDMARKER = new ElementType("ENDMARKER");
    public static final ElementType NEWLINE = new ElementType("NEWLINE");
    public static final ElementType KEYWORD = new ElementType("KEYWORD");
    public static final ElementType NAME = new ElementType("NAME");
    public static final ElementType OPERATOR = new ElementType("OPERATOR");
    public static final ElementType NUMBER = new ElementType("NUMBER");
    public static final ElementType STRING = new ElementType("STRING");

    public static final List<ElementType> ELEMENT_TYPES =
            List.of(
                    ENDMARKER,
                    NEWLINE,
                    KEYWORD,
                    NAME,
                    OPERATOR,
                    NUMBER,
                    STRING
            );

    public static final List<String> NAMES =
            ELEMENT_TYPES.stream().map(ElementType::getName).collect(Collectors.toList());
}

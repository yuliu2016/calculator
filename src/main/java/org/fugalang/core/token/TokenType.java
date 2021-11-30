package org.fugalang.core.token;

import org.fugalang.core.parser.ElementType;

import java.util.List;
import java.util.stream.Collectors;

public class TokenType {

    public static final ElementType ENDMARKER = new ElementType("ENDMARKER", false);
    public static final ElementType NEWLINE = new ElementType("NEWLINE", false);
    public static final ElementType NAME = new ElementType("NAME", false);
    public static final ElementType NUMBER = new ElementType("NUMBER", false);
    public static final ElementType STRING = new ElementType("STRING", false);

    public static final ElementType KEYWORD = new ElementType("KEYWORD", true);
    public static final ElementType OPERATOR = new ElementType("OPERATOR", true);

    public static final List<ElementType> ELEMENT_TYPES =
            List.of(
                    ENDMARKER,
                    NEWLINE,
                    NAME,
                    NUMBER,
                    STRING,
                    KEYWORD,
                    OPERATOR
            );

    public static final List<String> NAMES =
            ELEMENT_TYPES.stream().map(ElementType::name).collect(Collectors.toList());
}

package org.fugalang.core.token;

import org.fugalang.core.parser.ElementType;

import java.util.List;
import java.util.stream.Collectors;

public class TokenType {

    public static final ElementType ENDMARKER = ElementType.of("ENDMARKER", false);
    public static final ElementType NEWLINE = ElementType.of("NEWLINE", false);
    public static final ElementType NAME = ElementType.of("NAME", false);
    public static final ElementType NUMBER = ElementType.of("NUMBER", false);
    public static final ElementType STRING = ElementType.of("STRING", false);

    public static final ElementType KEYWORD = ElementType.of("KEYWORD", true);
    public static final ElementType OPERATOR = ElementType.of("OPERATOR", true);

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
            ELEMENT_TYPES.stream().map(ElementType::getName).collect(Collectors.toList());
}

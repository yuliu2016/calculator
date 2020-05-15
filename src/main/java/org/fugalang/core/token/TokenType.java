package org.fugalang.core.token;

import org.fugalang.core.grammar.gen.ConvertedValue;
import org.fugalang.core.grammar.util.ParserStringUtil;
import org.fugalang.core.parser.ElementType;

import java.util.List;
import java.util.Optional;
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

    public static Optional<ConvertedValue> checkToken(String s) {

        if (Keyword.ALL_KEYWORDS.contains(s)) {
            return Optional.of(new ConvertedValue("boolean", s, s));
        }
        if (Operator.CODE_TO_NAME.containsKey(s)) {
            var name = ParserStringUtil.convertCase(Operator.CODE_TO_NAME.get(s));
            return Optional.of(new ConvertedValue("boolean", name, s));
        }

        if (NAMES.contains(s)) {
            return Optional.of(new ConvertedValue("String", s.toLowerCase(), s));
        }

        return Optional.empty();
    }
}

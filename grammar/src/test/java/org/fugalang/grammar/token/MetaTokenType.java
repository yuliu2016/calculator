package org.fugalang.grammar.token;

import org.fugalang.core.parser.ElementType;

@Deprecated
public class MetaTokenType {
    public static final ElementType NEWLINE = ElementType.of("NEWLINE", false);
    public static final ElementType TOK = ElementType.of("TOK", false);
    public static final ElementType COL = ElementType.of(":", false);
    public static final ElementType LPAR = ElementType.of("(", false);
    public static final ElementType RPAR = ElementType.of(")", false);
    public static final ElementType LSQB = ElementType.of("[", false);
    public static final ElementType RSQB = ElementType.of("]", false);
    public static final ElementType PLUS = ElementType.of("+", false);
    public static final ElementType STAR = ElementType.of("*", false);
    public static final ElementType OR = ElementType.of("|", false);
}

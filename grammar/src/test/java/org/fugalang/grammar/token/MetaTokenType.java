package org.fugalang.grammar.token;

import org.fugalang.core.parser.ElementType;

@Deprecated
public class MetaTokenType {
    public static final ElementType NEWLINE = new ElementType("NEWLINE", false);
    public static final ElementType TOK = new ElementType("TOK", false);
    public static final ElementType COL = new ElementType(":", false);
    public static final ElementType LPAR = new ElementType("(", false);
    public static final ElementType RPAR = new ElementType(")", false);
    public static final ElementType LSQB = new ElementType("[", false);
    public static final ElementType RSQB = new ElementType("]", false);
    public static final ElementType PLUS = new ElementType("+", false);
    public static final ElementType STAR = new ElementType("*", false);
    public static final ElementType OR = new ElementType("|", false);
}

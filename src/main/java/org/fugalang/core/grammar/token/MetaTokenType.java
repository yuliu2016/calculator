package org.fugalang.core.grammar.token;

import org.fugalang.core.parser.ElementType;

public class MetaTokenType {
    public static final ElementType NEWLINE = new ElementType("NEWLINE");
    public static final ElementType TOK = new ElementType("TOK");
    public static final ElementType COL = new ElementType(":");
    public static final ElementType LPAR = new ElementType("(");
    public static final ElementType RPAR = new ElementType(")");
    public static final ElementType LSQB = new ElementType("[");
    public static final ElementType RSQB = new ElementType("]");
    public static final ElementType PLUS = new ElementType("+");
    public static final ElementType STAR = new ElementType("*");
    public static final ElementType OR = new ElementType("|");
}

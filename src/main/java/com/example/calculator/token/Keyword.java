package com.example.calculator.token;

import java.util.List;

public class Keyword {

    public static final String KW_RETURN = "return";
    public static final String KW_DEF = "def";
    public static final String KW_IF = "if";
    public static final String KW_ELSE = "else";
    public static final String KW_WHEN = "when";
    public static final String KW_AND = "and";
    public static final String KW_OR = "or";
    public static final String KW_NOT = "not";
    public static final String KW_IS = "is";
    public static final String KW_IN = "in";
    public static final String KW_PASS = "pass";
    public static final String KW_AS = "as";
    public static final String KW_FROM = "from";
    public static final String KW_IMPORT = "import";
    public static final String KW_WHILE = "while";
    public static final String KW_FOR = "for";
    public static final String KW_CONTINUE = "continue";
    public static final String KW_BREAK = "break";
    public static final String KW_TRY = "try";
    public static final String KW_EXCEPT = "except";
    public static final String KW_FINALLY = "finally";
    public static final String KW_RAISE = "raise";

    public static final List<String> ALL_KEYWORDS = List.of(
            // Functional keywords
            KW_RETURN, KW_DEF,
            // Condition keywords
            KW_IF, KW_ELSE, KW_WHEN,
            // Boolean comparisons
            KW_AND, KW_OR, KW_NOT, KW_IS, KW_IN,
            // Nothing
            KW_PASS,
            // Context keywords
            KW_AS, KW_FROM, KW_IMPORT,
            // Loop keywords
            KW_WHILE, KW_FOR, KW_CONTINUE, KW_BREAK,
            // Exception handling
            KW_TRY, KW_EXCEPT, KW_FINALLY, KW_RAISE
    );
}

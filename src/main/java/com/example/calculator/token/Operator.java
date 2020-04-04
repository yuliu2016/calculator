package com.example.calculator.token;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Includes all operators of the language. This includes delimiters
 * such as brackets
 */
public enum Operator {

    // ====== Single-char operators ======

    DOT("."),
    COMMA(","),
    ASSIGN("="),
    COLON(":"),
    DECORATOR("@"),

    // second most common - brackets

    OPEN_ROUND("("),
    CLOSE_ROUND(")"),

    OPEN_CURLY("{"),
    CLOSE_CURLY("}"),

    OPEN_SQUARE("["),
    CLOSE_SQUARE("]"),

    OPEN_ANGLE_LT("<"),
    CLOSE_ANGLE_MT(">"),

    // arithmetic

    PLUS("+"),
    MINUS("-"),
    TIMES("*"),
    DIV("/"),
    MODULUS("%"),

    // bitwise operators

    BIT_OR("|"),
    BIT_AND("&"),
    BIT_NOT("~"),
    BIT_XOR("^"),

    // ====== Double-Char Operators ======

    EQUAL("=="),
    NOT_EQUAL("!="),
    LESS_EQUAL("<="),
    MORE_EQUAL(">="),

    ARROW("=>"),
    ELVIS("?:"),

    FLOOR_DIV("//"),
    POWER("**"),

    PLUS_ASSIGN("+="),
    MINUS_ASSIGN("-="),
    TIMES_ASSIGN("*="),
    DIV_ASSIGN("/="),
    MODULUS_ASSIGN("%="),

    BIT_OR_ASSIGN("|="),
    BIT_AND_ASSIGN("&="),
    BIT_XOR_ASSIGN("^="),

    SHIFT_LEFT("<<"),
    SHIFT_RIGHT(">>"),

    // ====== Triple-char operators ======

    FLOOR_DIV_ASSIGN("//="),
    POWER_ASSIGN("**=");

    private final String code;

    Operator(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    private static Map<String, Operator> invertOperatorList(List<Operator> operators) {
        Map<String, Operator> map = new LinkedHashMap<>();

        for (var operator : operators) {
            map.put(operator.getCode(), operator);
        }

        return map;
    }

    public static final List<Operator> SINGLE_OPERATORS = List.of(
            DOT,
            COMMA,
            ASSIGN,
            COLON,
            DECORATOR,

            OPEN_ROUND,
            CLOSE_ROUND,
            OPEN_CURLY,
            CLOSE_CURLY,
            OPEN_SQUARE,
            CLOSE_SQUARE,
            OPEN_ANGLE_LT,   // typing and more-than comparison
            CLOSE_ANGLE_MT,  // typing and more-than comparison

            PLUS,
            MINUS,
            TIMES,           // times and def(*args)
            DIV,
            MODULUS,

            BIT_OR,
            BIT_AND,
            BIT_NOT,
            BIT_XOR
    );

    public static final Map<String, Operator> SINGLE_OPERATOR_MAP =
            invertOperatorList(SINGLE_OPERATORS);

    public static final List<Operator> DOUBLE_OPERATORS = List.of(
            EQUAL,
            NOT_EQUAL,
            LESS_EQUAL,
            MORE_EQUAL,

            ARROW,
            ELVIS,

            FLOOR_DIV,
            POWER,         // exponents and def(**kw args)/ {**k, **v} etc

            PLUS_ASSIGN,
            MINUS_ASSIGN,
            TIMES_ASSIGN,
            DIV_ASSIGN,
            MODULUS_ASSIGN,

            BIT_OR_ASSIGN,
            BIT_AND_ASSIGN,
            BIT_XOR_ASSIGN,

            SHIFT_LEFT,
            SHIFT_RIGHT
    );

    public static final Map<String, Operator> DOUBLE_OPERATOR_MAP =
            invertOperatorList(DOUBLE_OPERATORS);

    public static final List<Operator> TRIPLE_OPERATORS = List.of(
            FLOOR_DIV_ASSIGN,
            POWER_ASSIGN
    );

    public static final Map<String, Operator> TRIPLE_OPERATOR_MAP =
            invertOperatorList(TRIPLE_OPERATORS);
}

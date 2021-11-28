package org.fugalang.grammar.common;

/**
 * @param isLiteral Literal means that the string is matched exactly, e.g. 'return',
 *                  instead of referring to a class of tokens such as NUMBER.
 */
public record TokenEntry(
        int index,
        boolean isLiteral,
        String snakeCase,
        String literalValue
) {
}

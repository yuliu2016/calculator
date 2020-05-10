package org.fugalang.core.parser.context;

import org.fugalang.core.parser.ElementType;
import org.fugalang.core.parser.ParserElement;

public interface LexingContext {

    /**
     * Returns the current index state of the lexer
     *
     * @return the index
     */
    int index();

    /**
     * Sets the current index
     *
     * @param index the index to set
     * @throws IndexOutOfBoundsException if index is invalid
     * @throws IllegalStateException     if new index is less than index()
     */
    void setIndex(int index);

    /**
     * Returns the length of this string.
     * The length is equal to the number of <a href="Character.html#unicode">Unicode
     * code units</a> in the string.
     *
     * @return the length of the sequence of characters represented by this
     * object.
     */
    int length();

    /**
     * Returns the {@code char} value at the
     * specified index.
     *
     * @param index the index of the {@code char} value.
     * @return the {@code char} value at the specified index of the source string.
     * The first {@code char} value is at index {@code 0}.
     * @throws IndexOutOfBoundsException if index is invalid
     */
    char charAt(int index);

    /**
     * Returns a string that is a substring of this string. The
     * substring begins at the specified {@code beginIndex} and
     * extends to the character at index {@code endIndex - 1}.
     * Thus the length of the substring is {@code endIndex-beginIndex}.
     *
     * @param beginIndex the beginning index, inclusive.
     * @param endIndex   the ending index, exclusive.
     * @return the specified substring.
     * @throws IndexOutOfBoundsException indices are invalid
     */
    String substring(int beginIndex, int endIndex);

    /**
     * Create a shallow copy of this context at the current index,
     * then advances the copied index by a certain number of
     * characters
     *
     * @param n the number of characters to advance
     */
    LexingContext copyAndAdvance(int n);

    /**
     * Peek n characters into the future
     *
     * @param n number of characters to visit
     * @return the peeked string
     * @throws IndexOutOfBoundsException if n is too long
     */
    String peekOrThrow(int n);

    /**
     * Throws a syntax error at the current index
     *
     * @param message the error message
     */
    void syntaxError(String message);

    /**
     * Add a token to the sequence with line and column
     *
     * @param elementType the type of the token
     * @param beginIndex  the starting index, inclusive as in substring
     * @param endIndex    the ending index, exclusive as in substring
     */
    ParserElement createElement(ElementType elementType, int beginIndex, int endIndex);

    /**
     * Returns the number of lines that has been processed so far
     *
     * @return the number of lines
     */
    int lines();

    /**
     * Get the string representation of a specified line
     *
     * @param line the line to get
     * @return a substring representing the line, including newline characters
     * @throws IndexOutOfBoundsException if line is invalid or is more than
     *                                   th
     */
    String getLine(int line);

    /**
     * Decide if the source is long enough to peek
     *
     * @param n number of characters to visit
     * @return true if there is enough characters left to peek
     */
    default boolean canPeek(int n) {
        return (index() + n) <= length();
    }

    /**
     * Peek a single character into the future
     *
     * @return the peeked character
     */
    default char peekChar() {
        return charAt(index());
    }


    /**
     * Peek n characters into the future
     *
     * @param n number of characters to visit
     * @return the peeked string; or null if it exeeds string bounds
     */
    default String peekOrNull(int n) {
        return canPeek(n) ? peekOrThrow(n) : null;
    }

    /**
     * Advance the index by some amount
     *
     * @param n the number of characters to advance by
     */
    default void advance(int n) {
        setIndex(index() + n);
    }

    /**
     * Advance the index by one character
     */
    default void advance() {
        advance(1);
    }

    /**
     * @return true if there are more characters to be visited
     */
    default boolean hasRemaining() {
        return index() < length();
    }


    /**
     * Convenience method for peekChar
     *
     * @return peekChar()
     */
    default char p1() {
        return peekChar();
    }

    /**
     * Convenience method for peekOrNull
     *
     * @return peekOrNull(2)
     */
    default String p2() {
        return peekOrNull(2);
    }

    /**
     * Convenience method for peekOrNull
     *
     * @return peekOrNull(3)
     */
    default String p3() {
        return peekOrNull(3);
    }

    /**
     * Convenience method for peekOrNull
     *
     * @return peekOrNull(4)
     */
    default String p4() {
        return peekOrNull(4);
    }
}

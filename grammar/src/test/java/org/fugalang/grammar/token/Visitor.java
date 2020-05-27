package org.fugalang.grammar.token;

/**
 * Defines a visitor to a piece of code
 * Allows peeking and moving indices
 */
@Deprecated
public class Visitor {
    public String code;

    // the caret index
    public int i;

    // the length of the code
    public int size;

    // used to lookup up to the next three characters for convenience
    // p2 and p3 are optional because it could be reading into EOF
    public char p1;
    public String p2;
    public String p3;

    public Visitor(String code) {
        this(code, 0);
    }

    public Visitor(String code, int i) {
        this.code = code;
        this.i = i;

        size = code.length();
    }

    /**
     * Reset this visitor
     */
    public void resetState() {
        size = code.length();
        p1 = '\0';
        p2 = null;
        p3 = null;
    }

    /**
     * Create a shallow copy of this visitor at the current index,
     * then advances the copied index by a certain number of
     * characters
     *
     * @param n the number of characters to advance
     */
    public Visitor copyAndAdvance(int n) {
        return new Visitor(code, i + n);
    }

    /**
     * Decide if the string is long enough to peek
     *
     * @param n number of characters to visit
     * @return true if there is enough characters left to peek
     */
    public boolean canPeek(int n) {
        return (i + n) <= size;
    }

    /**
     * Peek n characters into the future
     *
     * @param n number of characters to visit
     * @return the peeked string
     * @throws IndexOutOfBoundsException if n is too long
     */
    public String peekOrThrow(int n) {
        return code.substring(i, i + n);
    }

    /**
     * Peek a single character into the future
     *
     * @return the peeked character
     */
    public char peekChar() {
        return code.charAt(i);
    }

    /**
     * Peek n characters into the future
     *
     * @param n number of characters to visit
     * @return the peeked string; or null if
     */
    public String peekOrNull(int n) {
        return canPeek(n) ? peekOrThrow(n) : null;
    }

    /**
     * Update the convenience fields
     */
    public void updateAllPeeks() {
        p1 = peekChar();
        p2 = peekOrNull(2);
        p3 = peekOrNull(3);
    }

    /**
     * @return true if there are more characters to be visited
     */
    public boolean hasRemaining() {
        return i < size;
    }
}

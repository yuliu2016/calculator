package org.fugalang.grammar.common;

public class TokenEntry {
    private final int index;
    /**
     * Literal means that the string is matched exactly, e.g. 'return',
     * instead of referring to a class of tokens such as NUMBER.
     */
    private final boolean isLiteral;
    private final String snakeCase;
    private final String literalValue;

    public TokenEntry(int index, boolean isLiteral, String snakeCase, String literalValue) {
        this.index = index;
        this.isLiteral = isLiteral;
        this.snakeCase = snakeCase;
        this.literalValue = literalValue;
    }

    public int getIndex() {
        return index;
    }

    public boolean isLiteral() {
        return isLiteral;
    }

    public String getNameSnakeCase() {
        return snakeCase;
    }

    public String getLiteralValue() {
        return literalValue;
    }
}

package org.fugalang.core.token;

public class Token {
    public final int line;
    public final int column;
    public final TokenType type;
    public final String value;

    public Token(int line, int column, TokenType type, String value) {
        this.line = line;
        this.column = column;
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Token{" +
                "line=" + line +
                ", column=" + column +
                ", type=" + type +
                ", value=" + value +
                '}';
    }
}

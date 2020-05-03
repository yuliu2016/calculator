package org.fugalang.core.token;

import org.fugalang.core.parser.ElementType;

public class Token {
    public final int line;
    public final int column;
    public final ElementType type;
    public final String value;

    public Token(int line, int column, ElementType type, String value) {
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

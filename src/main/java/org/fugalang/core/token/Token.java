package org.fugalang.core.token;

import org.fugalang.core.parser.ElementType;
import org.fugalang.core.parser.ParserElement;

public class Token implements ParserElement {
    private final int line;
    private final int column;
    private final ElementType type;
    private final String value;

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
                ", type=" + getType() +
                ", value=" + getValue() +
                '}';
    }

    @Override
    public ElementType getType() {
        return type;
    }

    @Override
    public int getIndex() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getLineStart() {
        return line;
    }

    @Override
    public int getLineEnd() {
        return column;
    }

    @Override
    public int getColumnStart() {
        return 0;
    }

    @Override
    public int getColumnEnd() {
        return 0;
    }

    @Override
    public String getValue() {
        return value;
    }
}

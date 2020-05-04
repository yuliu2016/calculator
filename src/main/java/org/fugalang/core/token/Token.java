package org.fugalang.core.token;

import org.fugalang.core.parser.ElementType;
import org.fugalang.core.parser.ParserElement;

public class Token implements ParserElement {
    private final ElementType type;
    private final String value;
    private final int index;
    private final int lineStart;
    private final int lineEnd;
    private final int columnStart;
    private final int columnEnd;

    public Token(ElementType type, String value, int index, int lineStart, int lineEnd, int columnStart, int columnEnd) {
        this.type = type;
        this.value = value;
        this.index = index;
        this.lineStart = lineStart;
        this.lineEnd = lineEnd;
        this.columnStart = columnStart;
        this.columnEnd = columnEnd;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type.getName() +
                ", value='" + value + '\'' +
                '}';
    }

    @Override
    public ElementType getType() {
        return type;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public int getLineStart() {
        return lineStart;
    }

    @Override
    public int getLineEnd() {
        return lineEnd;
    }

    @Override
    public int getColumnStart() {
        return columnStart;
    }

    @Override
    public int getColumnEnd() {
        return columnEnd;
    }

    @Override
    public String getValue() {
        return value;
    }
}

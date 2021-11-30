package org.fugalang.core.parser.impl;

import org.fugalang.core.parser.ElementType;
import org.fugalang.core.parser.ParserElement;

public record TokenElement(
        ElementType type,
        String value,
        int index,
        int lineStart,
        int lineEnd,
        int columnStart,
        int columnEnd
) implements ParserElement {

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

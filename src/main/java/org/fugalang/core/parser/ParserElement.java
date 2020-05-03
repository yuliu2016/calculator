package org.fugalang.core.parser;

public interface ParserElement {
    ElementType getType();

    int getIndex();

    int getLineStart();

    int getLineEnd();

    int getColumnStart();

    int getColumnEnd();

    String getValue();

    default boolean valueEquals(Object o) {
        return getValue().equals(o.toString());
    }
}

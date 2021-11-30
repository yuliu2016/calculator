package org.fugalang.core.parser;

public record ElementType(String name, boolean isLiteral) {

    @Override
    public String toString() {
        return name;
    }

    public static ElementType of(String name, boolean isLiteral) {
        return new ElementType(name, isLiteral);
    }
}

package org.fugalang.core.parser;

public class ElementType {
    private final String name;
    private final boolean isLiteral;

    private ElementType(String name, boolean isLiteral) {
        this.name = name;
        this.isLiteral = isLiteral;
    }

    public String getName() {
        return name;
    }

    public boolean isLiteral() {
        return isLiteral;
    }

    @Override
    public String toString() {
        return name;
    }

    public static ElementType of(String name, boolean isLiteral) {
        return new ElementType(name, isLiteral);
    }
}

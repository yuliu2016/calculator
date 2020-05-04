package org.fugalang.core.parser;

public class ElementType {
    private final String name;

    public ElementType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}

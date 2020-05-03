package org.fugalang.core.parser;

import java.util.function.Function;

public class ElementType {
    private final String name;
    private final Function<String, String> formatter;

    public ElementType(String name) {
        this(name, s -> s);
    }

    public ElementType(String name, Function<String, String> formatter) {
        this.name = name;
        this.formatter = formatter;
    }

    public String getName() {
        return name;
    }

    public Function<String, String> getFormatter() {
        return formatter;
    }
}

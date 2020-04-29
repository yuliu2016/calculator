package org.fugalang.core.parser;

public class ConjunctionRule implements ParserNode {
    public <T> T addRequired(String name, T value) {
        return value;
    }

    public boolean addRequired(String name, boolean value) {
        return value;
    }

    public <T> T addOptional(String name, T value) {
        return value;
    }

    public boolean addOptional(String name, boolean value) {
        return value;
    }
}

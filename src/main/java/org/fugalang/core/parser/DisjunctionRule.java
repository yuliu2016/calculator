package org.fugalang.core.parser;

public class DisjunctionRule implements ParserNode {

    public <T> T addChoice(String name, T choice) {
        return choice;
    }

    public boolean addChoice(String name, boolean value) {
        return value;
    }
}

package org.fugalang.core.grammar.classbuilder;

public class ClassField {
    private final String type;
    private final String name;

    public ClassField(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String asFieldDeclaration() {
        return String.format("public final %s %s;", type, name);
    }

    public String asConstructorArg() {
        return String.format("%s %s", type, name);
    }

    public String asConstructorStmt() {
        return String.format("this.%s = %s;", name, name);
    }
}

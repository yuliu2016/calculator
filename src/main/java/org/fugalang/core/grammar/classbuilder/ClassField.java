package org.fugalang.core.grammar.classbuilder;

import org.fugalang.core.grammar.gen.ParseStringUtil;

public class ClassField {
    private final ClassName className;
    private final String name;
    private final boolean isOptional;

    public ClassField(ClassName className, String name, boolean isOptional) {
        this.className = className;
        this.name = name;
        this.isOptional = isOptional;
    }

    public String asFieldDeclaration() {
        return String.format("private final %s %s;", className.asType(), name);
    }

    public String asConstructorArg() {
        return String.format("%s %s", className.asType(), name);
    }

    public String asConstructorStmt() {
        return String.format("this.%s = %s;", name, name);
    }

    public String asGetter() {
        StringBuilder sb = new StringBuilder();
        sb.append("    public ");

        if (isOptional) {
            sb.append(className.wrapIn("Optional").asType());
        } else {
            sb.append(className.asType());
        }

        sb.append(" get")
                .append(ParseStringUtil.capitalizeFirstCharOnly(name))
                .append("() {\n        return ");

        if (isOptional) {
            sb.append("Optional.ofNullable(")
                    .append(name)
                    .append(")");
        } else {
            sb.append(name);
        }

        sb.append(";\n    }\n");

        return sb.toString();
    }
}

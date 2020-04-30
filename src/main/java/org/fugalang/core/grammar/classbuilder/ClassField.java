package org.fugalang.core.grammar.classbuilder;

import org.fugalang.core.grammar.gen.ParseStringUtil;

public class ClassField {
    private final ClassName className;
    private final String fieldName;
    private final boolean isOptional;

    public ClassField(ClassName className, String fieldName, boolean isOptional) {
        this.className = className;
        this.fieldName = fieldName;
        this.isOptional = isOptional;
    }

    public String asFieldDeclaration() {
        return String.format("private final %s %s;", className.asType(), fieldName);
    }

    public String asConstructorArg() {
        return String.format("%s %s", className.asType(), fieldName);
    }

    public String asConstructorStmt() {
        return String.format("this.%s = %s;", fieldName, fieldName);
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
                .append(ParseStringUtil.capitalizeFirstCharOnly(fieldName))
                .append("() {\n        return ");

        if (isOptional) {
            sb.append("Optional.ofNullable(")
                    .append(fieldName)
                    .append(")");
        } else {
            sb.append(fieldName);
        }

        sb.append(";\n    }\n");

        return sb.toString();
    }

    public String asRuleStmt(RuleType parentRule) {
        switch (parentRule) {
            case Disjunction -> {
                return "addChoice(\"" + fieldName + "\", " + fieldName + ");";
            }
            case Conjunction -> {
                if (isOptional) {
                    return "addOptional(\"" + fieldName + "\", " + fieldName + ");";
                } else {
                    return "addRequired(\"" + fieldName + "\", " + fieldName + ");";
                }
            }
            default -> throw new IllegalArgumentException("ClassField does not support RuleType" + parentRule);
        }
    }
}

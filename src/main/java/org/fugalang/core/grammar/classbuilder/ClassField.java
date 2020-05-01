package org.fugalang.core.grammar.classbuilder;


public class ClassField {
    private final ClassName className;
    private final String fieldName;
    private final boolean isOptional;
    private final boolean isRepeated;

    public ClassField(ClassName className, String fieldName, boolean isOptional, boolean isRepeated) {
        this.className = className;
        this.fieldName = fieldName;
        this.isOptional = isOptional;
        this.isRepeated = isRepeated;

        if (isOptional && isRepeated) {
            throw new IllegalArgumentException("ClassField: Cannot be optional and repeated at the same time");
        }
    }

    public String getFieldName() {
        return fieldName;
    }

    public boolean isOptional() {
        return isOptional;
    }

    /**
     * Used for field name conflict resolution
     */
    public ClassField withFieldName(String newFieldName) {
        return new ClassField(className, newFieldName, isOptional, isRepeated);
    }

    @Override
    public String toString() {
        return "ClassField{" + asFieldDeclaration() + "}";
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

        sb.append(" ").append(fieldName).append("() {\n        return ");

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

    public String asRuleStmt(RuleType ruleType) {
        switch (ruleType) {
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
            default -> throw new IllegalArgumentException("ClassField does not support RuleType" + ruleType);
        }
    }

    public String asParserStmt(RuleType ruleType) {
        return "";
    }
}

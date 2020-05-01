package org.fugalang.core.grammar.classbuilder;


import org.fugalang.core.grammar.gen.ParserStringUtil;

public class ClassField {
    private final ClassName className;
    private final String fieldName;
    private final FieldType fieldType;
    private final ResultSource resultSource;

    public ClassField(ClassName className, String fieldName, FieldType fieldType, ResultSource resultSource) {
        this.className = className;
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.resultSource = resultSource;
    }

    public String getFieldName() {
        return fieldName;
    }

    public boolean isOptionalSingle() {
        return fieldType == FieldType.Optional;
    }

    public boolean isRequired() {
        return fieldType == FieldType.Required || fieldType == FieldType.RequiredList;
    }

    /**
     * Used for field name conflict resolution
     */
    public ClassField withFieldName(String newFieldName) {
        return new ClassField(className, newFieldName, fieldType, resultSource);
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

        if (isOptionalSingle()) {
            sb.append(className.wrapIn("Optional").asType());
        } else {
            sb.append(className.asType());
        }

        sb.append(" ").append(fieldName).append("() {\n        return ");

        if (isOptionalSingle()) {
            sb.append("Optional.ofNullable(")
                    .append(fieldName)
                    .append(")");
        } else {
            sb.append(fieldName);
        }

        sb.append(";\n    }\n");

        return sb.toString();
    }

    public String asNullCheck() {
        if (isOptionalSingle() || className.isNotNull()) {
            return "";
        }
        return "\n    public boolean has" +
                ParserStringUtil.capitalizeFirstCharOnly(fieldName) +
                "() {\n" +
                "        return " + fieldName + "() != null;\n" +
                "    }\n";
    }

    public String asRuleStmt(RuleType ruleType) {
        switch (ruleType) {
            case Disjunction -> {
                return "addChoice(\"" + fieldName + "\", " + fieldName + ");";
            }
            case Conjunction -> {
                if (isOptionalSingle()) {
                    return "addOptional(\"" + fieldName + "\", " + fieldName + ");";
                } else {
                    return "addRequired(\"" + fieldName + "\", " + fieldName + ");";
                }
            }
            default -> throw new IllegalArgumentException("ClassField does not support RuleType" + ruleType);
        }
    }

    public String asParserStmt(RuleType ruleType, boolean isFirst) {
        var resultExpr = getResultExpr();

        return switch (fieldType) {
            case Required -> getRequiredStmt(resultExpr, ruleType, isFirst);
            case Optional -> resultExpr + ";\n";
            case OptionalList -> getLoopStmt("", resultExpr);
            case RequiredList -> getLoopStmt(getRequiredStmt(resultExpr, ruleType, isFirst), resultExpr);
        };
    }

    private String getLoopStmt(String requiredStmt, String resultExpr) {
        return "parseTree.enterCollection();\n" + requiredStmt +
                "while (true) {\n" +
                "    var pos = parseTree.position();\n" +
                "    if (!" + resultExpr + " ||\n" +
                "            parseTree.guardLoopExit(pos)) {\n" +
                "        break;\n" +
                "    }\n" +
                "}\n" +
                "parseTree.exitCollection();\n";
    }

    private String getRequiredStmt(String resultExpr, RuleType ruleType, boolean isFirst) {
        return isFirst ?
                "result = " + resultExpr + ";\n" :
                switch (ruleType) {
                    case Conjunction -> "result = result && " + resultExpr + ";\n";
                    case Disjunction -> "result = result || " + resultExpr + ";\n";
                };
    }

    private String getResultExpr() {
        return switch (resultSource.getSourceType()) {
            case Class -> resultSource.getValue() + ".parse(parseTree, level + 1)";
            case TokenType -> "parseTree.consumeTokenType(\"" + resultSource.getValue() + "\")";
            case TokenLiteral -> "parseTree.consumeTokenLiteral(\"" + resultSource.getValue() + "\")";
        };
    }
}
